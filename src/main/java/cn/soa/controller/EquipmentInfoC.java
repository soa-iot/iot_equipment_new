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

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentInfo;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.EquipmentTypeBackup;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentBigEventSI;
import cn.soa.service.intel.EquipmentInfoSI;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import cn.soa.service.intel.EquipmentTypeBackupSI;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备台账信息处理控制层
 * @author Jiang, Hang
 * @since 2019-08-01
 */

@RestController
@Slf4j
@RequestMapping("/equipmentinfo")
public class EquipmentInfoC {
	
	@Autowired
	private EquipmentTypeBackupSI equipTypeBackupS;
	
	@Autowired
	private EquipmentInfoSI equipmentInfoS;
	
	/**
	 * 设备台账导入时备份数据库
	 * @param currentNodeName 设备类型
	 * @param currentUserName 操作人
	 * @param comment         操作意见
	 * @param type            操作类型
	 * @return
	 */
	@GetMapping("/import/backup")
	public ResultJson<String> backupEquipmentInfo(
			@RequestParam(required=true) String currentNodeName,
			@RequestParam(required=true) String currentUserName,
			@RequestParam(required=true) String comment,
			@RequestParam(required=true) String type ){
		
		log.info("---------开始备份设备信息-----------");
		log.info("------当前节点名：{}", currentNodeName);
		log.info("------当前用户名：{}", currentUserName);
		log.info("------当前操作备注：{}", comment);
		log.info("------当前操作类型：{}", type);
		
		if(currentNodeName == null || "".equals(currentNodeName)) {
			log.info("-------currentNodeName设备类型为null，备份失败---------");
			return new ResultJson<String>("");
		}
		
		EquipmentTypeBackup backup = new EquipmentTypeBackup();
		backup.setBname(currentNodeName);
		backup.setBperson(currentUserName);
		backup.setBnote(comment);
		backup.setBtype(type);
		backup.setBcreatetime(new Date());
		
		//调用业务层执行备份信息添加操作
		String result = equipTypeBackupS.addEquipmentBackupInfo(backup);
		
		return new ResultJson<String>(result);
	}
	
	/**
	 * 根据条件查询设备导入备份记录
	 * @param bname  设备类型
	 * @param startDate  开始时间
	 * @param endDate   结束时间
	 * @param bperson  操作人
	 * @param page    页数
	 * @param limit   每页条数
	 * @return
	 */
	@GetMapping("/query")
	public ResultJsonForTable<List<EquipmentTypeBackup>> queryEquipmentTypeBackupInfo(String bname, String startDate, String endDate, String bperson,
			                          Integer page, Integer limit){
		
		log.info("---------进入queryEquipmentTypeBackupInfo查询条件如下--------");
		log.info("------设备类型：{}", bname);
		log.info("------开始时间：{} ， 结束时间：{}", startDate, endDate);
		log.info("------操作人：{}", bperson);
		log.info("------分页信息： page={}, limit={}", page, limit);
		
		//调用业务层执行查询操作
		ResultJsonForTable<List<EquipmentTypeBackup>> result = equipTypeBackupS.findByCondition(bname, startDate, endDate, bperson, page, limit);
		return result;
	}
	
	/**
	 * 下载备份好的设备信息excel表
	 * @param bid
	 * @return
	 */
	@GetMapping("/download/{bid}")
	public ResponseEntity<byte[]> downloadExcel(@PathVariable("bid") String bid){
		log.info("---------进入downloadExcel开始下载excel表--------");
		log.info("---------bid = {}", bid);
		
		ResponseEntity<byte[]> result = equipTypeBackupS.findByBid(bid);
		
		return result;
	}
	
	/**
	 * 根据备份的数据进行还原
	 * @param bid
	 * @return
	 */
	@GetMapping("/rollback/{bid}")
	public ResultJson<Boolean> rollbackData(@PathVariable("bid") String bid){
		log.info("---------进入rollbackData开始数据还原--------");
		log.info("---------bid = {}", bid);
		try {
			boolean result = equipTypeBackupS.RollbackData(bid);
			return new ResultJson<>(result);
		}catch (Exception e) {
			return new ResultJson<>(false);
		}
		
	}
	
	/**
	 * 根据bid删除备份记录
	 * @param bid
	 * @return
	 */
	@GetMapping("/deletebackup/{bid}")
	public ResultJson<Void> deleteBackupData(@PathVariable("bid") String bid){
		log.info("---------进入deleteBackupData开始删除备份记录--------");
		log.info("---------bid = {}", bid);
		Integer row = equipTypeBackupS.deleteByBid(bid);
		if(row == null) {
			return new ResultJson<Void>(ResultJson.ERROR, "删除备份记录失败"+bid);
		}
		return new ResultJson<Void>(ResultJson.SUCCESS, "删除备份记录成功"+bid);
	}
	
	/**
	 * 根据条件查询设备信息
	 * @param bid
	 * @return
	 */
	@GetMapping("/show")
	public ResultJsonForTable<List<EquipmentInfo>> showEquipmentInfo(EquipmentInfo info, Integer page, Integer limit){
		log.info("---------进入showEquipmentInfo开始条件查询设备信息--------");
		log.info("---------查询条件：{}", info);
		log.info("------分页信息：page={}, limit={}", page, limit);
		
		return equipmentInfoS.getEquipmentInfo(info, page, limit);
	}
}
