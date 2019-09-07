package cn.soa.service.intel;

import java.util.List;

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
	
	/**
	 * 根据设备位号和年份查找出设备测厚记录数据
	 * @param  positionnum 设备位号
	 * @param  startYear 开始年份
	 * @param  endYear 结束年份
	 * @return 设备测厚记录
	 */
	EquipmentThickManagement findEquipRecordByPositionnumAndYear(
			String positionnum, String startYear, String endYear);
	
	/**
	 * 根据设备eid和测量时间查找出设备测厚记录数据
	 * @param  eid 设备id
	 * @param  measuretime 测量时间
	 * @return 设备测厚记录
	 */
	List<EquipmentThickRecord> findEquipRecordByEidAndTime(
			String eid, String measuretime);
	

}
