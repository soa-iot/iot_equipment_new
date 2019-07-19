package cn.soa.service.intel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;

/**
 * 设备大事件 业务层
 * @author Jiang, Hang
 * @since 2019-07-18
 */
public interface EquipmentBigEventSI {
	
	/**
	 * 根据设备位号查找设备大事件信息
	 * @param positionNum 设备位号
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 设备大事件信息列表
	 */
	ResultJsonForTable<List<EquipmentBigEvent>> findByPositionNum(
			@Param("positionNum") String positionNum, 
			@Param("page") Integer page, 
			@Param("limit") Integer limit);
	
	
	/**
	 * 插入设备大事件数据
	 * @param event 设备大事件数据
	 * @return 生效行数
	 */
	Integer addEvent(EquipmentBigEvent event);
}
