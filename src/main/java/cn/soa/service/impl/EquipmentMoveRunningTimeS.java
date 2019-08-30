package cn.soa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import cn.soa.utils.InfluxDBTemplate;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EquipmentMoveRunningTimeS implements EquipmentMoveRunningTimeSI {
	
	@Autowired
	private EquipmentMoveRunningTimeMapper equipMoveMapper;
	
	@Autowired
	private InfluxDBTemplate influxDBTemplate;
	
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
	 * @Title: getEquipmentRunningCount   
	 * @Description: 获取设备运行统计的全部设备数量  
	 * @return: Integer        
	 */
	@Override
	public Integer getEquipmentRunningCount() {
		try {
			Integer count = equipMoveMapper.countByCondition(new EquipmentMoveRunningTime());
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("后端查询报错");
		}
	}
	

	/**   
	 * @Title: getRunningEquipment   
	 * @Description: 获取需要监控运行时间的动设备  
	 * @return: Map<String,String>        
	 */ 
	@Override
	public List<EquipmentMoveRunningTime> getRunningEquipment(){
		log.info( "---S-------获取需要监控运行时间的动设备  -----");
		try {
			List<EquipmentMoveRunningTime> equipments = equipMoveMapper.findAll();
			if( equipments == null) return null;
//			log.info( equipments.toString() );
			return equipments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**   
	 * @Title: getRunningEquipmentNum   
	 * @Description:  获取所有动设备的分代号 
	 * @return: List<EquipmentMoveRunningTime>        
	 */  
	@Override
	public Map<String, Object> getRunningEquipmentNum(){
		log.info( "---S-------获取所有动设备的代号-----");
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
			log.info( positionNums.toString() );
			return positionNums;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	
	/**   
	 * @Title: getEquipPositionAndNumberAndDCS   
	 * @Description:   查询动设备位号对应的dcs位号和分代号
	 * @return: Map<String,Map<String,Object>>        
	 */  
	@Override
	public Map<String,Map<String,Object>> getEquipPositionAndNumberAndDCS(){
		log.info("--------查询动设备位号对应的dcs位号和分代号-------------");
		Map<String,Map<String,Object>> pnds = new HashMap<String,Map<String,Object>>();
		try {
			List<EquipmentMoveRunningTime> equipments = equipMoveMapper.findAll();
			if( equipments == null || equipments.size() < 1 ) return null;
			String position = null;
			String number = null;
			String dcsPosition = null;
			HashMap<String, Object> tempInfo = new HashMap<String,Object>();
			for( EquipmentMoveRunningTime e : equipments ) {
				position = e.getPositionNum();
				number = e.getRnumber();
				dcsPosition = e.getDcsPositionNum();
				tempInfo.put("dcsPosition", dcsPosition);
				tempInfo.put("number", number);
				pnds.put(position, tempInfo);
			}
			log.info(pnds.toString());;
			return pnds;
		} catch (Exception e) {
			e.printStackTrace();
			return pnds;
		}
	}
	
	/**   
	 * @Title: getEquipmentRunningAllS   
	 * @Description: 查找所有设备运行的设备  
	 * @return: List<EquipmentMoveRunningTime>        
	 */  
	@Override
	public List<EquipmentMoveRunningTime> getEquipmentRunningAllS(){
		try {
			List<EquipmentMoveRunningTime> allEquipRunnings = equipMoveMapper.findAll();
			return allEquipRunnings;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**   
	 * @Title: getEquipmentRunningS   
	 * @Description: 分页查询设备运行时间  
	 * @return: List<Map<String,Object>>        
	 */ 
	@Override
	public List<Map<String,Object>> getEquipmentRunningS( Map<String,Object> m, 
			String page, String size, String startTime, String endTime ) {
		log.info( "--------分页查询设备运行时间-------------" );
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			/*
			 * 获取需要统计的设备(参数中传入获取统计设备和全部动设备以前者优先级高)
			 */
			log.info( "-------- 获取需要统计的设备-------------" );
			List<Object> equips = getEquipmentRunningByCondition( m );
			log.info( "--------需要统计的运行设备有-------------" );
			log.info( equips.toString() );
			
			/*
			 * 根据条件查询统计数据
			 */
			log.info( "--------根据条件查询统计数据-------------" );
			List<Map<String, Object>> equipmentRunningData = 
					getEquipmentRunningData( equips, startTime, endTime );			
			
			/*
			 * 格式化数据
			 */
			log.info( "-------- 格式化数据 ------------" );
			data = formatEquipmentRunningData( equipmentRunningData );
			log.info( "-------- 格式化数据 ------------"  );
			log.info( data.toString());
						
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}
	
	/**   
	 * @Title: getEquipmentRunningByCondition   
	 * @Description:  获取需要统计的设备(参数中传入获取统计设备和全部动设备以前者优先级高)  
	 * @return: List<Object>        
	 */  
	public List<Object> getEquipmentRunningByCondition( Map<String,Object> m ){
		try {
			List<Object> equipsChoose = new ArrayList<Object>();
			List<EquipmentMoveRunningTime> tempEquipsChoose = new ArrayList<EquipmentMoveRunningTime>();
			Object equips = m.get( "equipment_number" );
			if( equips != null && StringUtils.isNotBlank( equips.toString() ) ) {
				Object[] equipsArr = StringUtils.split( equips.toString(), "," );
				equipsChoose = Arrays.asList( equipsArr );
			}else {
				tempEquipsChoose = equipMoveMapper.findAll();		
				log.info( tempEquipsChoose.toString() );
				if( tempEquipsChoose != null && tempEquipsChoose.size() > 0) {
					String positionNum = "";
					String number = "";
					for( EquipmentMoveRunningTime e : tempEquipsChoose ) {
						try {
							positionNum = e.getPositionNum();
							number = StringUtils.isBlank(e.getRnumber())?"1":e.getRnumber();
							if(StringUtils.isNotBlank(positionNum) && StringUtils.isNotBlank(number)) {
								equipsChoose.add(positionNum.trim() + "_" + number.trim());
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
					}
				}	
			}
					
			return equipsChoose;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**   
	 * @Title: getEquipmentRunningData   
	 * @Description: 根据条件查询统计数据  
	 * @return: List<Map<String,Object>>        
	 */  
	public List<Map<String,Object>> getEquipmentRunningData( 
			List<Object> equips, String startTime, String endTime ){
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();		
		try {
			//查询结果集
			String position_number = "";
			List<Series> series = null;
			List<String> columns = null;
			List<List<Object>> values = null;
			for( Object o : equips ) {
				try {
					if( o == null || StringUtils.isEmpty(o.toString())) continue;
					position_number = o.toString();
					String[] positionNumberArr = position_number.split("_");
					String sql = " select * from iot_equipment_running_dayMonitor "
							   + " where position = '" + positionNumberArr[0] 
							   + "' and number = '" + positionNumberArr[1]
							   + "' and time >= '" + startTime 
							   + "' and time <= '" + endTime 
							   + "' order by time ";
					System.out.println(sql);
					//结果集处理
					QueryResult results = influxDBTemplate.query(sql);					
					if( results == null ) continue;
					System.out.println(results.toString());
					series = results.getResults().get(0).getSeries();
					if( series == null ) continue;
					System.out.println(series.toString());
					columns = series.get(0).getColumns();
					values =  series.get(0).getValues();					
					for( List<Object> lo : values ) {
						Map<String, Object> tempData = new LinkedHashMap<String,Object>();
						tempData.put("position", positionNumberArr[0] );
						tempData.put("number", positionNumberArr[1] );
						for( int k = 0; k < columns.size(); k++ ) {
							tempData.put(columns.get(k).toString(), lo.get(k));
						}
						resultList.add(tempData);	
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				} 
			}		
			if(resultList!=null) log.info("----------结果：--------------" + resultList.toString());
			return resultList;
		} catch (Exception e) {
			e.printStackTrace(); 
			return resultList;
		}		
	}
	
	/**   
	 * @Title: formatEquipmentRunningData   
	 * @Description: 格式化返回数据  
	 * @return: List<Map<String,Object>>        
	 */  
	public List<Map<String,Object>> formatEquipmentRunningData( 
			List<Map<String, Object>> equipmentRunningData ){
		Map<String,Map<String, Object>> resultPart = new LinkedHashMap<String,Map<String, Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		String tempTime = "";
		try {
			for( Map<String, Object> m : equipmentRunningData ) {
				try {
					String position = "";
					String number = "";
					if(m != null && m.get("position")!=null ) {
						position = m.get("position").toString();
					}
					if( m.get("number")!=null) number = m.get("number").toString();
					tempTime = m.get("time").toString();
					tempTime = tempTime!=null?tempTime.substring(0, 10):tempTime;
					if( resultPart.containsKey(position)) {
						if( m.get("time") == null ) continue;
						resultPart.get(position).put(tempTime, m.get("sum"));
					}else {
						Map<String, Object> tempvalue = new HashMap<String, Object>();
						tempvalue.put("position", position);	
						tempvalue.put("number", number);
						tempvalue.put(tempTime, m.get("sum") );
						resultPart.put(position, tempvalue);
					}
				} catch (Exception e) {
					e.printStackTrace();
					//continue;
					break;
				}				
			}
			log.info("----------格式化返回数据结果1:  ----------");
			log.info(resultPart.toString());
			
			for( Entry<String,Map<String, Object>> e : resultPart.entrySet() ) {
				result.add(e.getValue());
			}
			log.info("----------格式化返回数据结果2:  ----------");
			log.info(result.toString());
			return  result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	
	/**   
	 * @Title: main   
	 * @Description: 测试方法  
	 * @return: void        
	 */  
	public static void main(String[] args) {
		EquipmentMoveRunningTimeS e = new EquipmentMoveRunningTimeS();
		List<Object> equips = new ArrayList<Object>();
		equips.add( "121-PI-101_1" );
		String time = "2019-8-1";
		String endtime = "2019-8-30";
		e.getEquipmentRunningData(equips, time, endtime);
		
	}
}
