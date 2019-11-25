package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentMoveInfo;
import cn.soa.entity.EquipmentMoveRunningTime;

/**
 * 动设备(运行时间)管理表 持久层
 * @author Jiang, Hang
 *
 */
@Mapper
public interface EquipmentMoveRunningTimeMapper {
	
	/**
	 * 分页查找动设备信息
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 动设备信息列表
	 */
	List<EquipmentMoveRunningTime> findByPage(
			@Param("equip") EquipmentMoveRunningTime equip, 
			@Param("page") Integer page, 
			@Param("limit") Integer limit);
	
	/**
	 * 根据条件统计动设备信息条数
	 * @param equip 查询条件
	 * @return 动设备信息条数
	 */
	Integer countByCondition(@Param("equip") EquipmentMoveRunningTime equip);
	
	/**
	 * 根据设备位号查询设备mrid
	 * @param equip 查询条件
	 * @return 设备mrid
	 */
	String findMridByPositionNum(String positionNum);

	/**   
	 * @Title: findAll   
	 * @Description: 查找所有的需要监控设备运行时间的设备  
	 * @return: List<EquipmentMoveRunningTime>        
	 */  
	List<EquipmentMoveRunningTime> findAll();
	/**
	 * 添加动设备信息
	 * @param equip 动设备信息对象
	 * @return 受影响行数
	 */
	Integer insertOne(@Param("equip") EquipmentMoveRunningTime equip);
	
	/**
	 * 分页查找动设备检维修信息
	 * @return List<EquipmentMoveInfo> - 动设备检维修信息
	 */
	List<EquipmentMoveInfo> selectEquipMoveInfo(
			@Param("positionNum") String positionNum, 
			@Param("equipName") String equipName,
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate,
			@Param("page") Integer page, 
			@Param("limit") Integer limit);
	
	/**
	 * 根据主键id查找动设备检维修信息
	 * @param mid - 主键id
	 * @return EquipmentMoveInfo - 动设备检维修信息
	 */
	EquipmentMoveInfo selectEquipMoveInfoByMid(@Param("mid") String mid);
	
	/**
	 * 统计动设备检维修信息条数
	 * @return 动设备检维修信息条数
	 */
	Integer countEquipMoveInfo(
			@Param("positionNum") String positionNum, 
			@Param("equipName") String equipName,
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate);
	
	/**
	 * 添加一条动设备检维修信息
	 * @param info - 动设备检维修信息
	 * @return 受影响行数
	 */
	Integer insertEquipMoveInfo(@Param("info") EquipmentMoveInfo info);
	
	/**
	 * 删除一条动设备检维修信息
	 * @param mid - 设备id
	 * @return 受影响行数
	 */
	Integer deleteEquipMoveInfo(String mid);
	
	/**
	 * 更新一条动设备检维修信息
	 * @param info - 动设备检维修信息
	 * @return 受影响行数
	 */
	Integer updateEquipMoveInfo(EquipmentMoveInfo info);
	
}
