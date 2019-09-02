package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;

/**
 * 设备大事件 持久层
 * @author Jiang, Hang
 * @since 2019-07-18
 */
@Mapper
public interface EquipmentBigEventMapper {
	
	/**
	 * 根据设备位号查找设备大事件信息
	 * @param positionNum 设备位号
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 设备大事件信息列表
	 */
	List<EquipmentBigEvent> findByPositionNum(
			@Param("positionNum") String positionNum, 
			@Param("page") Integer page, 
			@Param("limit") Integer limit);
	
	/**
	 * 统计查找设备大事件条数
	 * @param positionNum 设备位号
	 * @return 设备大事件信息列表
	 */
	Integer countEvent(@Param("positionNum") String positionNum);
	
	/**
	 * 插入设备大事件数据
	 * @param event 设备大事件数据
	 * @return 生效行数
	 */
	Integer addEvent(EquipmentBigEvent event);
	
	/**   
	 * @Title: findByEvent   
	 * @Description:统计查找设备指定类型的大事件   
	 * @return: List<EquipmentBigEvent>        
	 */  
	List<EquipmentBigEvent> findByEvent(@Param("positionNum") String positionNum,
			@Param("event") String event);
}
