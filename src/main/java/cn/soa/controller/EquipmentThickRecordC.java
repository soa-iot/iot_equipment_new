package cn.soa.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.EquipmentThickRecord;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentThickRecordSI;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备测厚处理控制层
 * @author Jiang, Hang
 * @since 2019-08-01
 */

@RestController
@Slf4j
@RequestMapping("/equipment/thick")
public class EquipmentThickRecordC {
	
	@Autowired
	private EquipmentThickRecordSI equipThickRecordS;
	
	
	/**
	 * 根据条件查询设备测厚记录
	 * @param record  查询条件
	 * @param startDate  开始时间
	 * @param endDate   结束时间
	 * @param page    页数
	 * @param limit   每页条数
	 * @return
	 */
	@GetMapping("/query")
	public ResultJsonForTable<List<EquipmentThickRecord>> queryEquipmentThickRecord(
			EquipmentThickRecord record, String startDate, String endDate, Integer page, Integer limit){
		
		log.info("---------进入queryEquipmentThickRecord查询条件如下--------");
		log.info("------设备测厚条件：{}", record);
		log.info("------开始时间：{} ， 结束时间：{}", startDate, endDate);
		log.info("------分页信息： page={}, limit={}", page, limit);
		
		//调用业务层执行查询操作
		ResultJsonForTable<List<EquipmentThickRecord>> result = equipThickRecordS.findByPositionNum(record, startDate, endDate, page, limit);
		return result;
	}
	
}
