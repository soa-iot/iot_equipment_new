package cn.soa.service.impl.lubrication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.lubrication.EquipmentLubricationMapper;
import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
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

	/**
	 * 新增油品
	 * @return
	 */
	@Override
	public Integer addLub(LubricateEquipmentPlace lubricateEquipmentPlace) {
		Integer row = 0;
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

}
