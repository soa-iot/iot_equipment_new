package cn.soa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.impl.EquipmentMoveRunningBYyearS;
import cn.soa.utils.InfluxDBTemplate;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/equipmen_influx")
public class EquipmentYearlyC {
	@Autowired
	private EquipmentMoveRunningBYyearS equipmentMoveRunningBYyear;
	
	@RequestMapping("/query")
	public ResultJsonForTable<List<Map<String, Object>>> CountByYear(String Time, String equipment_number,Integer page,Integer limit) {
		//测试
		log.info("---------------------Time---------------------:"+Time);
		log.info("---------------------equipment_number---------------------:"+equipment_number);
		log.info("---------------------page---------------------:"+page);
		log.info("---------------------limit---------------------:"+limit);
		Time = Time ==null || Time == ""?	 new SimpleDateFormat("yyyy").format(new Date()):Time;
		List<Map<String, Object>> result = equipmentMoveRunningBYyear.toStanderData(Time,equipment_number,page,limit);
		log.info("采集回来的数据："+result);
		if(result!=null||result.size()>0) {
			return new ResultJsonForTable<List<Map<String, Object>>>(0, "数据成功采集", equipmentMoveRunningBYyear.count(), result);
		}else {
			return new ResultJsonForTable<List<Map<String, Object>>>(1, "暂时未采集到数据", 0, null);
		}
	}
}
