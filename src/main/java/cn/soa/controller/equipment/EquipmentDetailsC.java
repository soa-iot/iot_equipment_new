package cn.soa.controller.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.controller.lubrication.EquipmentLubricationC;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.equipment.EquipmentDetails;
import cn.soa.service.intel.equipment.EquipmentDetailsSI;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备详情C
 * @author Bru.Lo
 *
 */
@RestController
@Slf4j
@RequestMapping("/equipmentdetails")
public class EquipmentDetailsC {

	@Autowired
	private EquipmentDetailsSI equipmentDetailsSI;
	
	@RequestMapping("/equipmentdetailsbyall")
	public ResultJsonForTable<List<EquipmentDetails>> findEquipmentDetails(String rfid, Integer page, Integer limit) {
		log.info("-----C-----位号："+rfid);
		log.info("-----C-----页数："+page);
		log.info("-----C-----每页数据量："+limit);
		
		if (rfid == null || "其他设备".equals(rfid)) {
			return new ResultJsonForTable<List<EquipmentDetails>>(1, "位号不能为空", 0, null);
		}else {
			List<EquipmentDetails> equipmentDetailses = equipmentDetailsSI.findEquipmentDetails(rfid, page, limit);
			List<EquipmentDetails> equipmentDetailseSize = equipmentDetailsSI.findEquipmentDetails(rfid, null, null);
			return	new ResultJsonForTable<List<EquipmentDetails>>(0, "数据查询成功", equipmentDetailseSize.size(), equipmentDetailses);
		}
		
	}
}
