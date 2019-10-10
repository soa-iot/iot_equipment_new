package cn.soa.service.impl.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.equipment.EquipmentDetailsMapper;
import cn.soa.entity.equipment.EquipmentDetails;
import cn.soa.service.intel.equipment.EquipmentDetailsSI;
import lombok.extern.slf4j.Slf4j;


/**
 * 设备详情S
 * @author Bru.Lo
 *
 */
@Slf4j
@Service
public class EquipmentDetailsS implements EquipmentDetailsSI {

	@Autowired
	private EquipmentDetailsMapper equipmentDetailsMapper;
	/**
	 * 设备详情S
	 * @param rfid
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public List<EquipmentDetails> findEquipmentDetails(String rfid, Integer page, Integer limit) {
		log.info("-----S-----位号："+rfid);
		log.info("-----S-----页数："+page);
		log.info("-----S-----每页数据量："+limit);
		
		return equipmentDetailsMapper.findEquipmentDetails(rfid, page, limit);
	}

}
