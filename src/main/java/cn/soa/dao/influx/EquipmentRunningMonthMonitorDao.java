package cn.soa.dao.influx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.utils.InfluxDBTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: EquipmentRunningMonthMonitor
 * @Description: influxdb数据库访问层
 * @author lxf
 */
@Service
@Slf4j
public class EquipmentRunningMonthMonitorDao {
	
	@Autowired
	private InfluxDBTemplate influxDBTemplate;
	//从iot_equipment_running_Monthmonitor查询每个月累计运行的时间
	public List<Map<String ,String>> getEquipmentRuningMonitor(String time){
		String command="SELECT value,pisition,runningDate FROM iot_equipment_running_Monthmonitor where runningDate=~"+time;
		log.info(command);
		//每个月查询累计运行时间
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("获取每个月的运行时间："+oneResult.toString());
		List<Map<String,String>> ls=new ArrayList<Map<String,String>>();
		if (oneResult.getSeries() != null) {
			List<List<Object>> valueList = oneResult.getSeries().stream().map(Series::getValues)
					.collect(Collectors.toList()).get(0);
			if (valueList != null && valueList.size() > 0) {
				for (List<Object> value : valueList) {
					Map<String, String> map = new HashMap<String, String>();
					// 数据库中字段1取值
						map.put("value", value.get(0).toString());
						map.put("position", value.get(1).toString());
						map.put("runningDate", value.get(2).toString());
						ls.add(map);
				}
			
			}
		}
		return ls;
		
	}
	//向iot_equipment_running_Monthmonitor插入数据
	public void insertEquipmentMonthMonitor(String startTime,String endTime) {
		String command="SELECT sum(value) FROM iot_equipment_running_Daymonitor where time>='"+startTime+" 00:00:00'"+"and time<='"+endTime+"23:59:59'"+" group by position order by time desc";
		log.info(command);
		//每个月查询累计运行时间
		BatchPoints batchPoints=BatchPoints
			    .database("lxf")
			    .build();
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("获取每个月的运行时间："+oneResult.toString());
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		if (oneResult.getSeries() != null) {
			List<List<Object>> valueList = oneResult.getSeries().stream().map(Series::getValues)
					.collect(Collectors.toList()).get(0);
			if (valueList != null && valueList.size() > 0) {
				for (List<Object> value : valueList) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 数据库中字段1取值
					Point point = Point.measurement("iot_equipment_running_Monthmonitor").tag("position", value.get(0).toString()).
							tag("runningDate", endTime.substring(0, 7))
									.addField("value",Integer.valueOf(value.get(2).toString()) )
									.build();
					 batchPoints.point(point);
				}
			
			}
		} 
		influxDBTemplate.batchInsert(batchPoints);
	}
	//统计设备每年累计运行时间
	public List<Map<String ,Object>> getEquipmentRuningMonitorTotal(String time){
		String command="SELECT sum(value) FROM iot_equipment_running_Monthmonitor where time>='"+time+"-01-01 00:00:00'"+"and time<='"+time+"12-31 23:59:59'"+" group by position order by time desc";
		log.info(command);
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("获取每年累计运行时间："+oneResult.toString());
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		if (oneResult.getSeries() != null) {
			List<List<Object>> valueList = oneResult.getSeries().stream().map(Series::getValues)
					.collect(Collectors.toList()).get(0);
			if (valueList != null && valueList.size() > 0) {
				for (List<Object> value : valueList) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 数据库中字段1取值
					map.put("position", value.get(0));
					map.put("value", value.get(1));
					ls.add(map);
				}
				
			}
		} 
		return ls;
		
	}
	//统计某一个设备大修或者更换后的运行时间
	public Float equipmentMonitorChangeOrFix(String startTime,String endTime,String  position) {
		String command="SELECT sum(value) FROM iot_equipment_running_monitor where time>='"+startTime+"and time<='"+endTime+"12-31 23:59:59'"+" and positionNum="+position;
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		Float f=(float) 0;
		if (oneResult.getSeries() != null) {
			List<List<Object>> valueList = oneResult.getSeries().stream().map(Series::getValues)
					.collect(Collectors.toList()).get(0);
			if (valueList != null && valueList.size() > 0) {
			f=	Float.parseFloat((String) valueList.get(0).get(0));
				
			}
		} 
		return f;
		
	}
	//统计设备总共的运行时间
	public List<Map<String ,Object>> getTotal(String time){
		String command="SELECT sum(value) FROM iot_equipment_running_monitor where time>='2018-01-01 00:00:00'and time<='"+time+"12-31 23:59:59'"+" group by position order by time desc";
		log.info(command);
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("获取每年累计运行时间："+oneResult.toString());
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		if (oneResult.getSeries() != null) {
			List<List<Object>> valueList = oneResult.getSeries().stream().map(Series::getValues)
					.collect(Collectors.toList()).get(0);
			if (valueList != null && valueList.size() > 0) {
				for (List<Object> value : valueList) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 数据库中字段1取值
					map.put("position", value.get(0));
					map.put("value", value.get(1));
					ls.add(map);
				}
				
			}
		} 
		return ls;
	}
}
