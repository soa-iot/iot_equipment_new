package cn.soa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentBigEventMapper;
import cn.soa.dao.influx.EquipmentRunningDayMonitorDao;
import cn.soa.entity.EquipmentBigEvent;
import cn.soa.service.intel.AddInfoToEquipementRunningSI;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AddInfoToEquipementRunningS
 * @Description: 为查询设备运行时间，添加附加信息
 * @author zhugang
 * @date 2019年8月24日
 */
@Service
@Slf4j
public class AddInfoToEquipementRunningS implements AddInfoToEquipementRunningSI{
	
	@Autowired
	private EquipmentBigEventMapper equipmentBigEventMapper;
	
	@Autowired
	private EquipmentRunningDayMonitorDao dayMonitorDao;
	
	/**   
	 * @Title: addRepaireInfo   
	 * @Description: 添加设备大修后运行时间  
	 * @return: List<Map<String,Object>>        
	 */  
	@Override
	public List<Map<String, Object>> addRepaireInfo( List<Map<String, Object>> list, String time) {
		log.info("------------- 添加设备大修后运行时间  ---------------");
		try {
			String currEquip = "";
			String currNumber = "";
		
			Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-6-28 00:00:00");
			Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time.toString());
			for( Map<String, Object> m : list) {
				try {
					if( m.get("position") == null ) continue;
					currEquip = m.get("position").toString();
					currNumber = m.get("number").toString();
					List<EquipmentBigEvent> events = 
							equipmentBigEventMapper.findByEvent(currEquip, "大修");
					//没有设备的大修时间，那以设备的更换时间作为开始时间
					if( events == null ) events = equipmentBigEventMapper.findByEvent(currEquip, "更换");
					if( events != null && events.size()>0  ) beginTime = events.get(events.size()-1).getEventTime();
					log.info("-----查询统计大修后查询起始时间-------" + beginTime);
					
					/*
					 * 查询统计大修后运行时间
					 */
					Object sumValue = dayMonitorDao.sumByPositionAndTime(currEquip, beginTime, endTime, currNumber);
					log.info(currEquip + "-----查询统计大修后运行时间-------" + sumValue);
					m.put("repairTime", sumValue);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}				
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}	
	}

	/**   
	 * @Title: addRepaireInfo   
	 * @Description: 添加设备切换后运行时间  
	 * @return: List<Map<String,Object>>        
	 */ 
	@Override
	public List<Map<String, Object>> addChangeInfo( List<Map<String, Object>> list, String time ) {
		log.info("------------- 添加设备切换后运行时间  ---------------");
		try {
			String currEquip = "";
			String currNumber = "";
		
			Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-6-28 00:00:00");
			Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time.toString());
			for( Map<String, Object> m : list) {				
				try {
					if( m.get("position") == null ) continue;
					currEquip = m.get("position").toString();
					currNumber = m.get("number").toString();
					List<EquipmentBigEvent> events = 
							equipmentBigEventMapper.findByEvent(currEquip, "切换");
					//没有设备的大修时间，那以设备的更换时间作为开始时间
					if( events == null && events.size()>0  ) events = equipmentBigEventMapper.findByEvent(currEquip, "更换");
					log.info(events.toString());
					if( events != null && events.size()>0 ) beginTime = events.get(events.size()-1).getEventTime();
					log.info("-----查询统计切换后查询起始时间-------" + beginTime);
					
					/*
					 * 查询统计切换后运行时间
					 */
					Object sumValue = dayMonitorDao.sumByPositionAndTime(currEquip, beginTime, endTime, currNumber);
					log.info(currEquip + "-----查询统计切换后运行时间-------" + sumValue);
					m.put("changeTime", sumValue);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}	
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}	
	}

	/**   
	 * @Title: addRepaireInfo   
	 * @Description: 添加设备总运行时间  
	 * @return: List<Map<String,Object>>        
	 */ 
	@Override
	public List<Map<String, Object>> addAllInfo( List<Map<String, Object>> list, String time ) {
		log.info("------------- 添加设备总运行时间  ---------------");
		try {
			String currEquip = "";
			String currNumber = "";
		
			Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-6-28 00:00:00");
			Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time.toString());
			for( Map<String, Object> m : list) {				
				try {
					if( m.get("position") == null ) continue;
					currEquip = m.get("position").toString();
					currNumber = m.get("number").toString();
					List<EquipmentBigEvent> events = equipmentBigEventMapper.findByEvent(currEquip, "更换");
					if( events != null && events.size()>0 ) beginTime = events.get(events.size()-1).getEventTime();
					log.info("-----查询统计总查询起始时间-------" + beginTime);
					
					/*
					 * 查询统计切换后运行时间
					 */
					Object sumValue = dayMonitorDao.sumByPositionAndTime(currEquip, beginTime, endTime, currNumber);
					log.info(currEquip + "-----查询统计总运行时间-------" + sumValue);
					m.put("allTime", sumValue);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}	
			}
				
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}	
	}
	
	/**   
	 * @Title: addCurrMonthInfo   
	 * @Description: 添加设备当月运行时间  
	 * @return: List<Map<String,Object>>        
	 */ 
	@Override
	public List<Map<String, Object>> addCurrMonthInfo( List<Map<String, Object>> list, 
			String startTimeStr, String endTimeStr ) {
		log.info("------------- 添加设备当月运行时间  ---------------");
		try {
			String currEquip = "";
			String currNumber = "";
			if( StringUtils.isBlank(startTimeStr)) return list;
			if( StringUtils.isBlank(endTimeStr)) return list;
		
			Date beginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startTimeStr.toString());
			Date endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endTimeStr.toString());
			for( Map<String, Object> m : list) {
				try {
					if( m.get("position") == null ) continue;
					currEquip = m.get("position").toString();
					currNumber = m.get("number").toString();
					
					/*
					 * 查询统计切换后运行时间
					 */
					Object sumValue = dayMonitorDao.sumByPositionAndTime(currEquip, beginTime, endTime, currNumber);
					log.info(currEquip + "-----查询统计当月运行时间-------" + sumValue);
					m.put("monthTime", sumValue);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}				
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	
}
