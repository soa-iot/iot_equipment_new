package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	 * 添加动设备信息
	 * @param equip 动设备信息对象
	 * @return 受影响行数
	 */
	Integer insertOne(@Param("equip") EquipmentMoveRunningTime equip);
}
