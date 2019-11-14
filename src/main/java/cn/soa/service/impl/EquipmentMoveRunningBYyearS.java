package cn.soa.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentMoveRunningCountTimeMapper;
import cn.soa.dao.influx.EquipmentRunningMonthMonitorDao;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.TimeStringOfLong;
import cn.soa.service.intel.EquipmentMoveRunningBYyearSI;
import cn.soa.utils.InfluxDBTemplate;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class EquipmentMoveRunningBYyearS implements EquipmentMoveRunningBYyearSI{
	@Autowired
	private EquipmentMoveRunningCountTimeMapper mapper;
	@Autowired
	private EquipmentRunningMonthMonitorDao dao;
	private DecimalFormat d = new DecimalFormat("0.00");
	//获取需要统计的设备总数
	public int count() {
		return mapper.findAll().size();
	}
	@Override
	//统计设备每个月的运行时间
	public List<Map<String, Object>> countEquipmentRunningEveryMonth(String Time,String equipment_number,Integer page,Integer limit) {
		List<EquipmentMoveRunningTime> list=mapper.findAll();
		List<Map<String, String>> ls=dao.getEquipmentRuningMonitor(Time,equipment_number,page,limit);
		log.info("获取到本年度所有设备每个月份的运行时间"+ls.toString());
		List<Map<String,Object>> equipments=new ArrayList<>();
		String[] months= {"January","February","March","April","May","June","July","August","September","October","November","December"};
		String position;
		for(int i=0;i<list.size();i++) {
			position=list.get(i).getPositionNum();
			
			for(Map<String, String> equipmentMonth:ls) {
				String position1 = equipmentMonth.get("position");
				if(position1.equals(position)) {
					Map<String,	Object> map=new HashMap<>();
					for(int a=0;a<months.length;++a) {
						map.put(months[a],0);
					}
					int mon=Integer.parseInt(equipmentMonth.get("runningDate").substring(5, 7))-1;
					map.put("position", position);
					map.put(months[mon],d.format(Float.parseFloat(equipmentMonth.get("value"))/60));//d.format(Float.parseFloat(equipmentMonth.get("value"))/60)
					equipments.add(map);
				}
				
			}
			
		}
		log.info("用于统计每个设备，每月运行的时间"+equipments.size());
		return equipments;
		
	}
	//统计设备初始化运行时间
	
	@Override
	public List<Map<String, Object>> countEquipmentRunningOrgTime() {
		// TODO Auto-generated method stub
		return mapper.countEquipmentRunningOrgTime();
	}
	//统计每个设备大修或者更换的时间
	@Override
	public String findChangeOrFixDate(String position, String event,String time) {
		List<Map<String,Object>> list=mapper.findChangeOrFixDate(position, event, time);
		if(list.size()==0) {
		 time=time+"-01-01 00:00:00";
		}else {
		time=(String) list.get(0).get("eventtime");
		}
		return time;
	}
	//统计每个设备大修或者更换后运行的时间
	public Double  equipmentMonitorChangeOrFix(String startTime,String endTime,String  position) {
		
		return dao.equipmentMonitorChangeOrFix(startTime,endTime,position);
		
	}
	//统计设备每年运行
	@Override
	public List<Map<String, Object>> countEquipmentRunningEveryYear(String time,String equipment_number,Integer page,Integer limit) {
		 List<Map<String, Object>> lists=dao.getEquipmentRuningMonitorTotal(time,equipment_number,page,limit);
		 String position;
		 //如果是2019年，需要加上初始话的时间
		if("2019".equals(time)) {
			if(lists!=null||lists.size()>0) {
				for(Map<String, Object> map:lists) {
					position=(String) map.get("position");
					for(Map<String, Object> orginTime:countEquipmentRunningOrgTime()) {
						if(position.equals(orginTime.get("positionnum"))) {
							map.put("value", Float.parseFloat(map.get("value").toString())+Float.parseFloat(orginTime.get("originalltime").toString()));
							break;
						}
					}
				}
			}
		}
		return lists;
	}
	//统计设备总共运行的时间
	public List<Map<String, Object>> getTotal(String time,String equipment_number,Integer page,Integer limit){
		 List<Map<String, Object>> lists=dao.getTotal(time,equipment_number,page,limit);
		 String position;
			if(lists!=null||lists.size()>0) {
				for(Map<String, Object> map:lists) {
					position=(String) map.get("position");
					for(Map<String, Object> orginTime:countEquipmentRunningOrgTime()) {
						if(position.equals(orginTime.get("position"))) {
							map.put("value", Float.parseFloat(map.get("value").toString())+Float.parseFloat(orginTime.get("value").toString()));
							break;
						}
					}
				}
			}
		
		return lists;
	}
	//数据整理，用于生成前端想要的数据格式
	public List<Map<String, Object>> toStanderData(String time,String equipment_number,Integer page,Integer limit){
		
		TimeStringOfLong strTime = new TimeStringOfLong();
		
		List<Map<String, Object>> montRunningTime=countEquipmentRunningEveryMonth(time,equipment_number,page,limit);
		List<Map<String, Object>>  yearRunningTime=countEquipmentRunningEveryYear(time,equipment_number,page,limit);
		List<Map<String, Object>> totalTime=getTotal(time,equipment_number,page,limit);
		log.info("统计本年度累计运行时间"+yearRunningTime.toString());
		if(montRunningTime!=null||montRunningTime.size()>0) {
			for(Map<String, Object> map:montRunningTime) {
				String position=map.get("position").toString();
				log.info("position:"+position);
				map.put("Modify_time",d.format(equipmentMonitorChangeOrFix(findChangeOrFixDate( position, "大修", time), time, position)/60));
				map.put("Chang_time",d.format(equipmentMonitorChangeOrFix( findChangeOrFixDate( position, "更换", time), time, position)/60));
				for(Map<String, Object> t:totalTime) {
					if(position.equals(t.get("position"))) {
						map.put("Total", d.format(Float.parseFloat(t.get("value").toString())/60));
						break;
					}
				}
				for(Map<String, Object> t:yearRunningTime) {
					if(position.equals(t.get("position"))) {
						map.put("Total_time", d.format(Float.parseFloat(t.get("value").toString())/60));
						break;
					}
				}
			}
		}
		return montRunningTime;
		
	}

}
