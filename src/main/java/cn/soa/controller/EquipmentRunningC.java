package cn.soa.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.impl.EquipmentMoveRunningTimeS;
import cn.soa.service.intel.AddInfoToEquipementRunningSI;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/equipmentRunning")
public class EquipmentRunningC {
	
	@Autowired
	private EquipmentMoveRunningTimeSI equipmentRunningS;
		
	@Autowired
	private AddInfoToEquipementRunningSI addInfoToRunningS;
	
	@GetMapping("")
	public ResultJson<List<EquipmentMoveRunningTime>> getAllEquipmentRunning(){
		log.info("-----获取所有的动设备-------");
		List<EquipmentMoveRunningTime> equipmentRunningAlls = equipmentRunningS.getEquipmentRunningAllS();
		return new ResultJson<List<EquipmentMoveRunningTime>>( 0, "查询结果", equipmentRunningAlls);
	}
	
	/**   
	 * @Title: getEquipmentRunningTimeC   
	 * @Description:   统计设备的运行时间
	 * @return: ResultJsonForTable<List<Map<String,Object>>>        
	 */  
	@GetMapping("/runningTime")
	public ResultJsonForTable<List<Map<String,Object>>> getEquipmentRunningTimeC(
			@RequestParam("page") @NotBlank String page ,
			@RequestParam("size") @NotBlank String size, 
			@RequestParam Map<String,Object> params ){
		log.info( "----------统计设备的运行时间-----------" );
		//参数验证
		if( params == null ) return null;
		Object startTime = params.get( "startTime" );
		Object endTime = params.get( "endTime" );
		log.info( "startTime:"+startTime );
		log.info( "endTime:"+endTime );
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		//log.info( "endTime2:"+sdf.format(new  Date()));
		if( startTime == null || StringUtils.isEmpty(startTime) ) startTime = sdf.format(new  Date()) + "-01 00:00:00"; 
		if( endTime == null || StringUtils.isEmpty(endTime) ) endTime = sdf.format(new  Date()) + "-30 23:59:59.99999"; 
		log.info( params.toString() );
		
		//查询
		List<Map<String, Object>> runningData = equipmentRunningS
				.getEquipmentRunningS(params, page, size, startTime.toString(), endTime.toString());
		
		runningData = addInfoToRunningS.addRepaireInfo(runningData, endTime.toString());
		runningData = addInfoToRunningS.addChangeInfo(runningData, endTime.toString());
		runningData = addInfoToRunningS.addAllInfo(runningData, endTime.toString());
		runningData = addInfoToRunningS.addCurrMonthInfo(
				runningData, startTime.toString(), endTime.toString());
		
//		log.info( runningData.toString() );
		
		//计算总数
		Integer counts = 0;
		try {
			counts = equipmentRunningS.getEquipmentRunningCount();		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultJsonForTable<List<Map<String,Object>>>( 
					1, e.getMessage(), 0, runningData );
		}
		log.info( "获取设备运行统计的全部设备数量: " + counts );
		
		//返回数据
		return new ResultJsonForTable<List<Map<String,Object>>>( 
				0, "查询完成", counts, runningData );
	}
}
