package cn.soa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;

@Service
public class EquipmentMoveRunningTimeS implements EquipmentMoveRunningTimeSI {
	private static Logger logger = LoggerFactory.getLogger( EquipmentMoveRunningTimeS.class );
	
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
	 * @Title: getRunningEquipment   
	 * @Description: 获取需要监控运行时间的动设备  
	 * @return: Map<String,String>        
	 */ 
	@Override
	public List<EquipmentMoveRunningTime> getRunningEquipment(){
		logger.info( "---S-------获取需要监控运行时间的动设备  -----");
		try {
			List<EquipmentMoveRunningTime> equipments = equipMoveMapper.findAll();
			if( equipments == null) return null;
			logger.info( equipments.toString() );
			return equipments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**   
	 * @Title: getRunningEquipmentNum   
	 * @Description:  获取所有动设备的代号 
	 * @return: List<EquipmentMoveRunningTime>        
	 */  
	@Override
	public Map<String, Object> getRunningEquipmentNum(){
		logger.info( "---S-------获取所有动设备的代号-----");
		Map<String, Object> positionNums = new HashMap<String,Object>();
		try {
			List<EquipmentMoveRunningTime> equipments = getRunningEquipment();
			for( EquipmentMoveRunningTime e : equipments ) {
				String currNum = e.getRnumber();
				if( currNum != null ) {
					positionNums.put( e.getPositionNum(), e.getRnumber() );
				}else {
					positionNums.put( e.getPositionNum(), "1" );
				}				
			}
			if( positionNums.isEmpty() ) return null;
			logger.info( positionNums.toString() );
			return positionNums;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
}
