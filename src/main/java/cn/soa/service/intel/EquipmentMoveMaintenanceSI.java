package cn.soa.service.intel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentMoveInfo;

public interface EquipmentMoveMaintenanceSI {
	
	
	/**
	 * 分页查找动设备检维修信息
	 * @return List<EquipmentMoveInfo> - 动设备检维修信息
	 */
	List<EquipmentMoveInfo> getEquipMoveInfo(
			String positionNum, String equipName,String startDate, String endDate,Integer page, Integer limit);
	
	/**
	 * 根据主键id查找动设备检维修信息
	 * @param mid - 主键id
	 * @return EquipmentMoveInfo - 动设备检维修信息
	 */
	List<EquipmentMoveInfo> getEquipMoveInfo(String[] mid);
	
	
	/**
	 * 统计动设备检维修信息条数
	 */
	Integer countEquipMoveInfo(String positionNum, String equipName,String startDate, String endDate);
	
	/**
	 * 添加一条动设备检维修信息
	 * @param info - 动设备检维修信息
	 * @return 受影响行数
	 */
	boolean insertEquipMoveInfo(EquipmentMoveInfo info);
	
	/**
	 * 删除一条动设备检维修信息
	 * @param mid - 设备id
	 * @return 受影响行数
	 */
	boolean deleteEquipMoveInfo(String mid);
	
	/**
	 * 更新一条动设备检维修信息
	 * @param info - 动设备检维修信息
	 * @return 受影响行数
	 */
	boolean updateEquipMoveInfo(EquipmentMoveInfo info);

}
