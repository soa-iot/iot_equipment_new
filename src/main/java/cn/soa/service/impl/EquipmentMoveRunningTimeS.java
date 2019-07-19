package cn.soa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;

@Service
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
	
}
