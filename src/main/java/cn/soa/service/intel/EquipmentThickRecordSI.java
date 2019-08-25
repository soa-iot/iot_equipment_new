package cn.soa.service.intel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.EquipmentThickManagement;
import cn.soa.entity.EquipmentThickRecord;
import cn.soa.entity.ResultJsonForTable;

/**
 * 设备测厚记录业务层
 * @author Jiang, Hang
 * @since 2019-08-22
 */
public interface EquipmentThickRecordSI {
	
	/**
	 * 根据条件查找出设备测厚记录数据
	 * @param  equipRecord 设备测厚查询条件
	 * @return 设备测厚记录列表
	 */
	ResultJsonForTable<List<EquipmentThickRecord>> findEquipRecord(
			EquipmentThickRecord record, String startDate, String endDate, Integer page, Integer limit);
	
	/**
	 * 新增设备测厚数据
	 * @param  equipThick 设备数据
	 * @return 结果
	 */
	String addEquipThick(EquipmentThickManagement equipThick);
	
	/**
	 * 根据条件查找出设备测厚数据
	 * @param  equipRecord 设备测厚查询条件
	 * @return 设备测厚列表
	 */
	ResultJsonForTable<List<EquipmentThickManagement>> findEquipThick(
			EquipmentThickManagement equipThick, Integer page, Integer limit);

}
