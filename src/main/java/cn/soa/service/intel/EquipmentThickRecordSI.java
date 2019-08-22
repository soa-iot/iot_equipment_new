package cn.soa.service.intel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
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
	ResultJsonForTable<List<EquipmentThickRecord>> findByPositionNum(
			EquipmentThickRecord record, String startDate, String endDate, Integer page, Integer limit);
	
}
