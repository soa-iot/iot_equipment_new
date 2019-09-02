package cn.soa.controller;

import java.util.ArrayList;
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
	public ResultJsonForTable CountByYear(String Time ) {
		List<Map<String, Object>> result=equipmentMoveRunningBYyear.toStanderData(Time);
		if(result!=null||result.size()>0) {
			return new ResultJsonForTable(1, "数据成功采集", equipmentMoveRunningBYyear.count(), result);
		}else {
			return new ResultJsonForTable(0, "暂时未采集到数据", 0, null);
		}
	}
}
