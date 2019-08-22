package cn.soa.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.RunningEquipments;
import cn.soa.service.impl.EquipmentMoveRunningTimeS;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import cn.soa.utils.InfluxDBTemplate;

@Service
public class EquipmentRunningMonitor {
	private static Logger logger = LoggerFactory.getLogger( EquipmentRunningMonitor.class );
	
	@Autowired
	private RunningEquipments runningEquipments;
	
	@Autowired
	private EquipmentMoveRunningTimeSI eRunningTimeS;
	
	@Autowired
	private InfluxDBTemplate influxDBTemplate;
	
	/**   
	 * @Title: getRunningEquipment   
	 * @Description: 保存监控数据   
	 * @return: Map<String,String>        
	 */  
	public Map<String,Object> startMonitorDataT(){
		logger.info( "--T------保存监控数据 --------" );
		try {
			
			/*
			 * 获取所有需要监控点位信息
			 */
			List<String> positionNums = runningEquipments.runningPositions;	
			if( positionNums == null || positionNums.size() < 1 ) return null;
			logger.info( "--T------需要监控的点位信息--------" );
			logger.info( positionNums.toString() );
			
			
			/*
			 * 通过opc获取DCS运行数据 
			 */
			Map<String, Object> opcValues = getOpcValue(positionNums);
			
			
			/*
			 * 保存监控数据
			 */
			if( opcValues.isEmpty() ) return null;
			//获取所有动设备的代号
			Map<String, Object> positionNumbers = eRunningTimeS.getRunningEquipmentNum();
			boolean b = saveMonitorData( opcValues, positionNumbers );
			if( b ) {
				logger.info( "--T------保存实时设备运行监控数据成功--------" );
				return opcValues;
			}
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**   
	 * @Title: getOpcValue   
	 * @Description:   从opc获取DCS数据
	 * @return: Map<String,Object>        
	 */  
	public Map<String,Object> getOpcValue( List<String> positionNums){
		Map<String, Object> positionValues = new HashMap<String,Object>();
		try {
			//暂时模拟数据
			for( String s : positionNums ) {
				positionValues.put( s, new Random().nextInt() );
			}
			if( positionValues.size() < 1 ) return null;
			logger.info( positionValues.toString() );
			return positionValues;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**   
	 * @Title: saveMonitorData   
	 * @Description: 保存数据到infliuxdb  
	 * @return: boolean        
	 */  
	public boolean saveMonitorData( Map<String,Object> positionValuesMap
			, Map<String,Object> positionNumMap ) {
		try {
			//批量插入数据
			String measurement = "iot_equipment_running_monitor";
			Map<String, String> tags = new HashMap<String,String>();
			Map<String, Object> fields = new HashMap<String,Object>();			
			BatchPoints batchPoints = BatchPoints
				    .database("jhc2")
				    .consistency(InfluxDB.ConsistencyLevel.ALL)
				    .build();			
			for( Entry<String,Object> s : positionValuesMap.entrySet() ){
				String position = s.getKey();
				Object value = s.getValue();
				tags.put( "positionNum", position );
				String number = (String) positionNumMap.get( position );
				if( number != null && !number.isEmpty()) {
					tags.put( "number", number );
				}else {
					tags.put( "number", "1" );
				}
				fields.put( "timeValue", "10" );//此处需要修改，改为定时器设定的多少分钟采集一次数据，动态
				Point point = influxDBTemplate.pointBuilder( measurement, tags, fields);
				batchPoints.point( point );
			}
		
			influxDBTemplate.batchInsert( batchPoints );			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
