package cn.soa.service.impl.lubrication;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.lubrication.EquipmentLubricationMapper;
import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.LubricationMothlyReport;
import cn.soa.entity.LubricationRecordReport;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;
import cn.soa.service.intel.lubrication.EquipmentLubricationSI;
import cn.soa.utils.ExportExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备润滑 业务层
 * @author Luo Guimao
 * 
 */
@Service
@Slf4j
public class EquipmentLubricationS implements EquipmentLubricationSI{

	@Autowired
	private EquipmentLubricationMapper equipmentLubricationMapper;
	
	@Autowired
	private EquipmentLubricationOilMapper equipmentLubricationOilMapper;

	/**
	 * 新增油品部位
	 * @return
	 */
	@Override
	public Integer addLub(LubricateEquipmentPlace lubricateEquipmentPlace) {
		Integer row = 0;
		
		Date nextchangetime = nextDateUtil(lubricateEquipmentPlace);		
		lubricateEquipmentPlace.setNextchangetime(nextchangetime);
		
		List<LubricateEquipment> lubricateEquipments = equipmentLubricationMapper.findLubEqui(new LubricateEquipment().setLnamekey(lubricateEquipmentPlace.getLnamekey()));

		log.info("========================lubricateEquipments:"+lubricateEquipments);

		if (lubricateEquipments != null && lubricateEquipments.size() > 0) {
			lubricateEquipmentPlace.setLid(lubricateEquipments.get(0).getLid());
		}else {
			row = equipmentLubricationMapper.insertLubEqui(lubricateEquipmentPlace);
		}

		String lid = lubricateEquipmentPlace.getLid();
		log.info("=======================lid:"+lid);
		if (lid !=null && !lid.equals("")) {
			return equipmentLubricationMapper.insertLubPlace(lubricateEquipmentPlace);
		}

		return 0;
	}

	/**
	 * 按条件分页查询换油设备
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  换油设备数据列表
	 */
	@Override
	public ResultJsonForTable<List<LubricateEquipment>> findEquipLubricationByPage(LubricateEquipment equip, Integer page, Integer limit) {
		log.info("------开始按条件分页查询换油设备------");
		//先统计总数量
		Integer total = equipmentLubricationMapper.countEquipLubricationByPage(equip);
		if(total == null || total == 0) {
			log.info("------按条件查询换油设备总数为：count={}------", total);
			return new ResultJsonForTable<List<LubricateEquipment>>(0, "查询结束", 0, null);
		}
		//分页查询
		List<LubricateEquipment> result = equipmentLubricationMapper.findEquipLubricationByPage(equip, page, limit);
		log.info("------按条件分页查询换油设备结束，查询条数为： count={}------", total);
		return new ResultJsonForTable<List<LubricateEquipment>>(0, "查询成功", total, result);
	}

	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 润滑设备lid
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  设备换油记录数据列表
	 */
	@Override
	public ResultJsonForTable<List<LubricateEquipmentRecord>> findEquipLubricationRecordByLid(String lid, Integer page, Integer limit) {
		log.info("------开始根据设备lid查询设备换油记录数据------");
		//先统计总数量
		Integer total = equipmentLubricationMapper.countEquipLubricationRecordByLid(lid);
		System.err.println("total: "+total);
		if(total == null || total == 0) {
			log.info("------按lid查询设备换油记录总数为：count={}------", total);
			return new ResultJsonForTable<List<LubricateEquipmentRecord>>(0, "查询结束", 0, null);
		}
		//分页查询
		List<LubricateEquipmentRecord> result = equipmentLubricationMapper.findEquipLubricationRecordByLid(lid, page, limit);
		log.info("------根据设备lid查询设备换油记录结束，查询条数为： count={}------", total);
		return new ResultJsonForTable<List<LubricateEquipmentRecord>>(0, "查询成功", total, result);
	}
	
	/**
	 * 临时换油日期跟踪
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param page 第几页
	 * @param limit 每页条数
	 * return 
	 */
	@Override
	public ResultJsonForTable<List<LubricateEquipmentPlace>> findEquipLubricationTrace(String positionnum, String tname, Integer page,
			Integer limit) {
		log.info("------开始临时换油日期跟踪------");
		//先统计总数量
		Integer total = equipmentLubricationMapper.countEquipLubricationTrace(positionnum, tname);
		System.err.println("total: "+total);
		if(total == null || total == 0) {
			log.info("------临时换油跟踪总数为：count={}------", total);
			return new ResultJsonForTable<List<LubricateEquipmentPlace>>(0, "查询结束", 0, null);
		}
		//分页查询
		List<LubricateEquipmentPlace> result = equipmentLubricationMapper.findEquipLubricationTrace(positionnum, tname, page, limit);
		log.info("------查询临时换油跟踪结束，查询条数为： count={}------", total);
		return new ResultJsonForTable<List<LubricateEquipmentPlace>>(0, "查询成功", total, result);
	}
	
	/**
	 * 按月统计每种润滑油使用量
	 */
	@Override
	public List<LubricationMothlyReport> findRecordByYear(String year) {
		log.info("------开始根据年份查询每种润滑油月度使用量------");
		List<LubricationMothlyReport> result = equipmentLubricationMapper.findRecordByYear(year);
		log.info("------根据年份查询每种润滑油月度使用量结束------");

		return result;
	}
	
	/**
	 * 分页查询设备润滑油加油和换油记录
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param page 第几页
	 * @param limit 每页条数
	 */
	@Override
	public ResultJsonForTable<List<LubricationRecordReport>> findLubricationRecordByPage(String positionnum, String tname,
			String startDate, String endDate, Integer page, Integer limit) {
		log.info("------开始分页查询设备润滑油加油和换油记录------");
		//先统计总数量
		Integer total = equipmentLubricationMapper.countLubricationRecord(positionnum, tname, startDate, endDate);
		System.err.println("total: "+total);
		if(total == null || total == 0) {
			log.info("------设备润滑油加油和换油记录总数为：count={}------", total);
			return new ResultJsonForTable<List<LubricationRecordReport>>(0, "查询结束", 0, null);
		}
		//分页查询
		List<LubricationRecordReport> result = equipmentLubricationMapper.findLubricationRecordByPage(positionnum, tname, startDate, endDate, page, limit);
		log.info("------查询设备润滑油加油和换油记录，查询条数为： count={}------", total);
		return new ResultJsonForTable<List<LubricationRecordReport>>(0, "查询成功", total, result);
	}
	
	/**
	 * 查询设备润滑油加油和换油记录
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 */
	@Override
	public List<LubricationRecordReport> findLubricationRecord(String positionnum, String tname,
			String startDate, String endDate) {
		log.info("------开始查询设备润滑油加油和换油记录------");
		
		return equipmentLubricationMapper.findLubricationRecord(positionnum, tname, startDate, endDate);
	}

	/**
	 * 查询换油部位
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public List<LubricateEquipmentPlace> findLubPlace(Integer page, Integer limit) {
		List<LubricateEquipmentPlace> lubricateEquipmentPlaces = equipmentLubricationMapper.findLubPlace(page, limit);
		
		//将周期放入一个字段中
		Integer rn = (page - 1) * limit;
		if (lubricateEquipmentPlaces != null && lubricateEquipmentPlaces.size() > 0) {
			for (LubricateEquipmentPlace lubricateEquipmentPlace : lubricateEquipmentPlaces) {
				String pfrequency = lubricateEquipmentPlace.getPfrequency();
				String punit = lubricateEquipmentPlace.getPunit();
				//将周期和单位合并在一起
				if (pfrequency != null && punit != null) {
					pfrequency += punit;
					lubricateEquipmentPlace.setPfrequency(pfrequency);
					lubricateEquipmentPlace.setRn(++rn);
				}
			}
		}
		
		return lubricateEquipmentPlaces;
	}

	@Override
	public Integer findLubPlaceCount() {
		return equipmentLubricationMapper.findLubPlaceCount();
	}

	/**
	 * 根据位号和换油部位查询换油部位
	 * @param lnamekey
	 * @param pplace
	 * @return
	 */
	@Override
	public LubricateEquipmentPlace findLubPlaceByNamekey(String lnamekey, String pplace) {
		return equipmentLubricationMapper.findLubPlaceByNamekey(lnamekey, pplace);
	}

	/**
	 * 更新润滑部位最后一次时间和下一次换油时间
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	//必须传ptime、excutor操作人、pid、ramount加油量
	@Override
	public Integer updateLuEqPlByPid(LubricateEquipmentRecord lubricateEquipmentRecord) {
		
		log.info("-----S-----lubricateEquipmentRecord——pid:"+lubricateEquipmentRecord.getPid()+",加换油时间："+lubricateEquipmentRecord.getPtime()+",加油量："+lubricateEquipmentRecord.getRamount());
		//查询油品部位数据
		LubricateEquipmentPlace lubricateEquipmentPlace = new LubricateEquipmentPlace();
		lubricateEquipmentPlace.setPid(lubricateEquipmentRecord.getPid());
		
		lubricateEquipmentPlace = equipmentLubricationMapper.findLuEqPlByAll(lubricateEquipmentPlace);
		log.info("--------S----------lubricateEquipmentPlace:"+lubricateEquipmentPlace);
		
		//更新最后一次换油时间和下一次换油时间
		lubricateEquipmentPlace.setLastchangetime(lubricateEquipmentRecord.getPtime());
		lubricateEquipmentPlace.setNextchangetime(nextDateUtil(lubricateEquipmentPlace));
		
		Integer updateRow = equipmentLubricationMapper.updateLuEqPlByPid(lubricateEquipmentPlace);
		log.info("-------S------------更新润滑部位行数："+updateRow);
		
		//获取油品信息
		EquipmentLubricationOil equipmentLubricationOil = new EquipmentLubricationOil();
		equipmentLubricationOil.setOname(lubricateEquipmentPlace.getRequireoil1());
		List<EquipmentLubricationOil> equipmentLubricationOils = equipmentLubricationOilMapper.findOilbyConditions(equipmentLubricationOil);
		
		lubricateEquipmentRecord.setLid(lubricateEquipmentPlace.getLid());
		lubricateEquipmentRecord.setOid(equipmentLubricationOils.get(0).getOid());
		
		Integer insetRow = equipmentLubricationMapper.insertLubRecord(lubricateEquipmentRecord);
		log.info("--------S-----------S插入换油记录行数："+updateRow);
		//出入库记录
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 计算下一次换油时间
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	public Date nextDateUtil(LubricateEquipmentPlace lubricateEquipmentPlace) {
		
		//取最后一次换油时间
				Date lastchangetime = lubricateEquipmentPlace.getLastchangetime();
				//取换油周期
				Integer pfrequency = Integer.valueOf(lubricateEquipmentPlace.getPfrequency());
				//取周期单位
				String punit = lubricateEquipmentPlace.getPunit();
				
				//计算下一次换油时间
				Calendar nextchangetime = Calendar.getInstance();
				nextchangetime.setTime(lastchangetime);
				
				if ("日".equals(punit)) {
					nextchangetime.add(Calendar.DATE, pfrequency);
				}else if("月".equals(punit)) {
					nextchangetime.add(Calendar.MONTH, pfrequency);
				}else if("年".equals(punit)) {
					nextchangetime.add(Calendar.YEAR, pfrequency);
				}
		return nextchangetime.getTime();
		
	}
	
}
