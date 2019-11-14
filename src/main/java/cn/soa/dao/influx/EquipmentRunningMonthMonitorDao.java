package cn.soa.dao.influx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import cn.soa.entity.TimeStringOfLong;
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
	private TimeStringOfLong strTime = new TimeStringOfLong();
	//从iot_equipment_running_Monthmonitor查询每个月累计运行的时间
	public List<Map<String ,String>> getEquipmentRuningMonitor(String time,String equipment_number,Integer page,Integer limit){
		String command="SELECT value,position,runningDate FROM iot_equipment_running_Monthmonitor where runningDate=~/"+time+"/";
		if (equipment_number != null && equipment_number.length()>2) command += " and position='" + equipment_number + "'";
		command += " limit "+limit+" offset "+(page - 1) * limit;
		log.info("========================================:"+command);
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
						map.put("value", value.get(1).toString());
						map.put("position", value.get(2).toString());
						map.put("runningDate", value.get(3).toString());
						ls.add(map);
				}
			
			}
		}
		return ls;
		
	}
	//向iot_equipment_running_Monthmonitor插入数据
	public void insertEquipmentMonthMonitor(Long startTime,Long endTime) {
		String command="SELECT sum(sum) FROM iot_equipment_running_dayMonitor where time>="+startTime+" and time<="+endTime+" group by position order by time desc";
		//String command="SELECT sum(sum) FROM iot_equipment_running_Daymonitor where time>='2019-09-01 00:00:00' and time<='2019-09-30 00:00:00' group by position order by time desc";
		log.info(command);
		//每个月查询累计运行时间
		BatchPoints batchPoints=BatchPoints
			    .database("jhc1")
			    .build();
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
	
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		if (oneResult.getSeries() != null) {
			//这里数据获取有问题
			for(Series s:oneResult.getSeries()) {
				log.info(s.getTags().get("position"));
				log.info(s.getValues().toString());
				Point point = Point.measurement("iot_equipment_running_Monthmonitor").tag("position", s.getTags().get("position")).
						tag("runningDate", new SimpleDateFormat("yyyy-MM").format(new Date(startTime/1000000)))
								.addField("value",Float.parseFloat(s.getValues().get(0).get(1).toString()))
								.build();
				 batchPoints.point(point);
			}
		} 
		influxDBTemplate.batchInsert(batchPoints);
	}
	//统计设备每年累计运行时间
	public List<Map<String ,Object>> getEquipmentRuningMonitorTotal(String time,String equipment_number,Integer page,Integer limit){
		
		String command = "SELECT value FROM iot_equipment_running_Monthmonitor where time>=" + strTime.getTime(time + "-01-01 00:00:00") + " and time<=" + strTime.getTime(time + "-12-31 23:59:59");
		if (equipment_number != null && equipment_number.length()>2) command += " and position='" + equipment_number+"'";
		command += " group by position" + " limit " + limit + " offset " + (page - 1) * limit;
		log.info("command："+command);
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("获取每年累计运行时间："+oneResult.toString());
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		
		if (oneResult.getSeries() != null) {
			for(Series s:oneResult.getSeries()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 数据库中字段1取值
				map.put("position", s.getTags().get("position"));
				map.put("value", s.getValues().get(0).get(1));
				ls.add(map);
				}
		
		} 
		return ls;
		
	}
	//统计某一个设备大修或者更换后的运行时间
	public Double  equipmentMonitorChangeOrFix(String startTime,String endTime,String  position) {
		String command="SELECT sum(value) FROM iot_equipment_running_monitor where time>='"+startTime+"' and time<='"+endTime+"-12-31 23:59:59'"+" and position='"+position+"'";
		
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("+++++++++++++++++++oneResult："+oneResult);
		Double  f = null;
		if (oneResult.getSeries() != null) {
			List<List<Object>> valueList = oneResult.getSeries().stream().map(Series::getValues)
					.collect(Collectors.toList()).get(0);
			if (valueList != null && valueList.size() > 0) {
			f=	 (Double ) valueList.get(0).get(1);
			}
		} 
		log.info("统计某一个设备大修或者更换后的运行时间"+f);
		return f;
		
	}
	//统计设备总共的运行时间
	public List<Map<String ,Object>> getTotal(String time,String equipment_number,Integer page,Integer limit){
		String command="SELECT sum(value) FROM iot_equipment_running_monitor where time>='2018-01-01 00:00:00'and time<='"+time+"-12-31 23:59:59'";
		if(equipment_number != null && equipment_number.length()>2) command += " and position='" + equipment_number+"'";
		command += " group by position" + " limit " + limit + " offset " + (page - 1) * limit;
		log.info(command);
		QueryResult results = influxDBTemplate.query(command);
		Result oneResult = results.getResults().get(0);
		log.info("获取每年累计运行时间："+oneResult.toString());
		List<Map<String,Object>> ls=new ArrayList<Map<String,Object>>();
		if (oneResult.getSeries() != null) {
			for(Series s:oneResult.getSeries()) {
//				log.info(s.getTags().get("position"));
//				log.info(s.getValues().toString());
					Map<String, Object> map = new HashMap<String, Object>();
					// 数据库中字段1取值
					map.put("position", s.getTags().get("position"));
					map.put("value", Float.parseFloat(s.getValues().get(0).get(1).toString()));
					ls.add(map);
			}
		} 
		//统计的设备总共运行的时间
		log.info("统计的设备总共运行的时间"+ls.toString());
		return ls;
	}

}
