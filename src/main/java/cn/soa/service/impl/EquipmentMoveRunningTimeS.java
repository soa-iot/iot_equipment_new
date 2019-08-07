package cn.soa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EquipmentMoveRunningTimeS implements EquipmentMoveRunningTimeSI {
	
	@Autowired
	private EquipmentMoveRunningTimeMapper equipMoveMapper;
	
	/**
	 * 分页查找动设备信息
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 动设备信息列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultJsonForTable<List<EquipmentMoveRunningTime>> findByPage(EquipmentMoveRunningTime equip, Integer page,
			Integer limit) {
		
		Integer count = equipMoveMapper.countByCondition(equip);
		if(count == null || count == 0) {
			return new ResultJsonForTable(0, "无数据", 0, null);
		}
		List<EquipmentMoveRunningTime> result = equipMoveMapper.findByPage(equip, page, limit);
		
		return new ResultJsonForTable(0, "success", (result==null)?0:count, result);
	}
	
	/**
	 * 添加动设备数据
	 * @param equip 动设备信息
	 * @return 是否添加成功
	 */
	@Override
	public String addOne(EquipmentMoveRunningTime equip) {
		String mrid = equipMoveMapper.findMridByPositionNum(equip.getPositionNum());
		if(mrid != null) {
			log.info("------添加动设备数据失败，设备位号重复-----positionNum={}",equip.getPositionNum());
			return "添加动设备数据失败，设备位号重复";
		}
		
		try {
			Integer row = equipMoveMapper.insertOne(equip);
			if(row != null && row != 0) {
				log.info("------添加动设备数据成功-----");
				return "success";
			}
			
		}catch (Exception e) {
			log.info("------插入动设备数据数据失败："+equip);
			log.info(e.getMessage());
			e.printStackTrace();
		}

		return "添加动设备数据失败";
	}
	
}
