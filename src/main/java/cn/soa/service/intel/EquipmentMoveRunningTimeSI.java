package cn.soa.service.intel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;

/**
 * 动设备(运行时间)管理 业务层
 * @author Jiang, Hang
 * @since 2019-07-18
 */
public interface EquipmentMoveRunningTimeSI {
	
	/**
	 * 分页查找动设备信息
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 动设备信息列表
	 */
	ResultJsonForTable<List<EquipmentMoveRunningTime>> findByPage(
			EquipmentMoveRunningTime equip, Integer page, Integer limit);
	
	/**
	 * 添加动设备数据
	 * @param equip 动设备信息
	 * @return 是否添加成功
	 */
	String addOne(EquipmentMoveRunningTime equip);
}
