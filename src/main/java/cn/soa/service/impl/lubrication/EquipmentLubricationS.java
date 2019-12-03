package cn.soa.service.impl.lubrication;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.lubrication.EquipmentLubricationMapper;
import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.LubricationMothlyReport;
import cn.soa.entity.LubricationRecordReport;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;
import cn.soa.service.intel.lubrication.EquipmentLubricationSI;
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

	@Autowired
	private EquipmentLubricationOilSI equipmentLubricationOilSI;
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
	public List<LubricateEquipmentPlace> findLubPlace(Integer page, Integer limit,String nextchangetime) {
		
		List<LubricateEquipmentPlace> lubricateEquipmentPlaces = equipmentLubricationMapper.findLubPlace(page, limit,nextchangetime);
		
		//将周期放入一个字段中
		Integer rn=0;
		if (page != null && limit != null) {
			rn = (page - 1) * limit;
		}
		
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
	public Integer findLubPlaceCount(String nextchangetime) {
		
		return equipmentLubricationMapper.findLubPlaceCount(nextchangetime);
	}

	/**
	 * 根据位号和换油部位查询换油部位
	 * @param lnamekey
	 * @param pplace
	 * @return
	 */
	@Override
	public List<LubricateEquipmentPlace> findLubPlaceByNamekey(String lnamekey, String pplace) {
		return equipmentLubricationMapper.findLubPlaceByNamekey(lnamekey, pplace);
	}

	/**
	 * 设备加换油S
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	//必须传ptime、excutor操作人、pid、ramount加油量、rtype加换油类型
	@Override
	@Transactional
	public Integer updateLuEqPlByPid(LubricateEquipmentRecord lubricateEquipmentRecord, String rtype) {
		String oname = lubricateEquipmentRecord.getRequireoil1();
		log.info("------------s-------------oname:"+oname);
		//加换油时间
		Date ptime = lubricateEquipmentRecord.getPtime();
		log.info("-----S-----lubricateEquipmentRecord——pid:"+lubricateEquipmentRecord.getPid()+"，操作人："+lubricateEquipmentRecord.getExcutor()+",加/换油时间："+ptime+",加/换油量："+lubricateEquipmentRecord.getRamount()+",加/换油类型："+rtype);
		
		//查询油品部位数据
		LubricateEquipmentPlace lubricateEquipmentPlace = new LubricateEquipmentPlace();
		lubricateEquipmentPlace.setPid(lubricateEquipmentRecord.getPid());
		
		lubricateEquipmentPlace = equipmentLubricationMapper.findLuEqPlByAll(lubricateEquipmentPlace);
		lubricateEquipmentPlace.setRequireoil1(oname);
		log.info("--------S----------lubricateEquipmentPlace:"+lubricateEquipmentPlace);
		
		Integer updateRow=null;
		if ("换油".equals(rtype) || "换脂".equals(rtype)) {
			
			//更新最后一次换油时间和下一次换油时间
			lubricateEquipmentPlace.setLastchangetime(ptime);
			lubricateEquipmentPlace.setNextchangetime(nextDateUtil(lubricateEquipmentPlace));
			
			updateRow = equipmentLubricationMapper.updateLuEqPlByPid(lubricateEquipmentPlace);
			log.info("-------S------------更新润滑部位行数："+updateRow);
		}
		
		//获取油品信息
		EquipmentLubricationOil equipmentLubricationOil = new EquipmentLubricationOil();
		equipmentLubricationOil.setOname(lubricateEquipmentPlace.getRequireoil1());
		//equipmentLubricationOil.setOname(oname);
		List<EquipmentLubricationOil> equipmentLubricationOils = equipmentLubricationOilMapper.findOilbyConditions(equipmentLubricationOil);
		
		String oid = equipmentLubricationOils.get(0).getOid();
		lubricateEquipmentRecord.setLid(lubricateEquipmentPlace.getLid());
		lubricateEquipmentRecord.setOid(oid);
		lubricateEquipmentRecord.setOperatetype(rtype);
		
		Integer insetRow = equipmentLubricationMapper.insertLubRecord(lubricateEquipmentRecord);
		log.info("--------S-----------S插入换油记录行数："+insetRow);
		
		Double ostock = Double.valueOf(equipmentLubricationOils.get(0).getOstock());
		Double ramount = Double.valueOf(lubricateEquipmentRecord.getRamount());
		//出入库记录
		EquipmentOilRecord equipmentOilRecord = new EquipmentOilRecord();
		equipmentOilRecord.setOid(oid);
		equipmentOilRecord.setRtime(ptime);                           
		equipmentOilRecord.setRinout("减");                                 
		equipmentOilRecord.setRamount(lubricateEquipmentRecord.getRamount());
		equipmentOilRecord.setUserid(lubricateEquipmentRecord.getExcutor());                              
		equipmentOilRecord.setRtype(rtype);  
		equipmentOilRecord.setRstock(ostock-ramount);
		
		Integer insertRecordRow = equipmentLubricationOilMapper.insertRecord(equipmentOilRecord );
		log.info("-----S-----插入油品出入库数据数量："+insertRecordRow);
		Integer updateStockRow = equipmentLubricationOilMapper.updateStock(-ramount, oid);
		log.info("-----S-----更新油品数据数量："+updateStockRow);
		if (insetRow > 0 && insertRecordRow > 0 && updateStockRow > 0) { 
			if ("换油".equals(rtype) || "换脂".equals(rtype)) {
				if (updateRow > 0) {
					return 1;
				}else {
					return 0;
				}
			}else {
				return 1;
			}
			
		}else {
			return 0;
		}
		
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
