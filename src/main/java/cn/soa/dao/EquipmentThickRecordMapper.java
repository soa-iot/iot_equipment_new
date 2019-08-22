package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentThickRecord;

/**
 * 设备测厚记录持久层接口
 * @author Jiang, Hang
 *
 */
@Mapper
public interface EquipmentThickRecordMapper {
	
	/**
	 * 根据条件查找出设备测厚记录数据
	 * @param  equipRecord 设备测厚查询条件
	 * @return 设备测厚记录列表
	 */
	List<EquipmentThickRecord> findEquipRecord(
			@Param("equipRecord") EquipmentThickRecord equipRecord, 
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate, 
			@Param("page") Integer page, 
			@Param("limit") Integer limit);
	
	/**
	 * 统计条件查询设备测厚记录总条数
	 * @param  equipRecord 设备测厚查询条件
	 * @return 设备测厚记录总条数
	 */
	Integer countEquipRecord(
			@Param("equipRecord") EquipmentThickRecord equipRecord, 
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate);
}
