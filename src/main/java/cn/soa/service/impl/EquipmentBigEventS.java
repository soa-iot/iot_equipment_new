package cn.soa.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.EquipmentBigEventMapper;
import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentBigEventSI;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EquipmentBigEventS implements EquipmentBigEventSI {
	
	@Autowired
	private EquipmentBigEventMapper equipEventMapper;
	
	@Autowired
	private EquipmentMoveRunningTimeMapper equipMoveMapper;
	
	/**
	 * 根据设备位号查找设备大事件信息
	 * @param positionNum 设备位号
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 设备大事件信息列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultJsonForTable<List<EquipmentBigEvent>> findByPositionNum(String positionNum, Integer page,
			Integer limit) {
		
		Integer count = equipEventMapper.countEvent(positionNum);
		if(count == null || count == 0) {
			return new ResultJsonForTable(0, "无数据", 0, null);
		}
		
		List<EquipmentBigEvent> result = equipEventMapper.findByPositionNum(positionNum, page, limit);
		return new ResultJsonForTable(0, "success", (result==null?0:count), result);
	}
	
	/**
	 * 插入设备大事件数据
	 * @param event 设备大事件数据
	 * @return 生效行数
	 */
	@Override
	@Transactional
	public Integer addEvent(EquipmentBigEvent event) {
		//根据设备位号查找对应的mrid
		String mrid = equipMoveMapper.findMridByPositionNum(event.getPositionNum());
		if(mrid == null) {
			log.error("---------设备位号{}对应的mrid不存在", event.getPositionNum());
			return null;
		}
		//大修事件对象中加入mrid，执行插入操作
		event.setMrid(mrid);
		try {
			Integer rows = equipEventMapper.addEvent(event);
			return rows;
		}catch (Exception e) {
			log.error("---设备大事件数据插入失败：{}", event);
			e.printStackTrace();
			return null;
		}
	}

}
