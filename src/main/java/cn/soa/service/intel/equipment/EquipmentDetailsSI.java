package cn.soa.service.intel.equipment;

import java.util.List;

import cn.soa.entity.equipment.EquipmentDetails;

/**
 * 设备详情S
 * @author Bru.Lo
 *
 */
public interface EquipmentDetailsSI {

	/**
	 * 设备详情S
	 * @param rfid
	 * @param page
	 * @param limit
	 * @return
	 */
	List<EquipmentDetails> findEquipmentDetails(String rfid,Integer page,Integer limit);
}
