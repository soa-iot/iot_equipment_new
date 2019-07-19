package cn.soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentBigEventSI;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * 动设备信息处理控制层
 * @author Jiang, Hang
 * @since 2019-07-18
 */

@RestController
@Slf4j
@RequestMapping("/equipment")
public class EquipmentC {
	
	@Autowired
	private EquipmentMoveRunningTimeSI equipMoveS;
	@Autowired
	private EquipmentBigEventSI equipEventS;
	
	@PostMapping("/list")
	public ResultJsonForTable<List<EquipmentMoveRunningTime>> queryEquipmentByPage(
			EquipmentMoveRunningTime equip, Integer page, Integer limit){
		
		log.info("------动设备查询条件：{}", equip);
		log.info("------分页信息：page={}, limit={}", page, limit);
		
		return equipMoveS.findByPage(equip, page, limit);
	}
	
	@PostMapping("/event/{positionNum}")
	public ResultJsonForTable<List<EquipmentBigEvent>> queryEventByPage(
			@PathVariable("positionNum") String positionNum, Integer page, Integer limit){
		
		log.info("------动设备大事件位号：{}", positionNum);
		log.info("------分页信息：page={}, limit={}", page, limit);
		
		return equipEventS.findByPositionNum(positionNum, page, limit);
	}
	
	@PostMapping("/event/add")
	public ResultJson<Void> addEvent(EquipmentBigEvent event){
		
		log.info("------动设备大事件添加信息：", event);
		
		Integer result = equipEventS.addEvent(event);
		if(result == null) {
			return new ResultJson<>(ResultJson.ERROR, "插入动设备大事件失败");
		}
		return new ResultJson<>(ResultJson.SUCCESS, "插入动设备大事件成功");
	}
}
