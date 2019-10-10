package cn.soa.dao.equipment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.equipment.EquipmentDetails;

/**
 * 设备详情dao接口
 * @author Bru.Lo
 *
 */
@Mapper
public interface EquipmentDetailsMapper {
	
	/**
	 * 设备详情dao
	 * @param rfid
	 * @return
	 */
	List<EquipmentDetails> findEquipmentDetails(
			@Param("rfid")String rfid,
			@Param("page")Integer page,
			@Param("limit")Integer limit);

}
