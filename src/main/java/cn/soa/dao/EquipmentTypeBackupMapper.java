package cn.soa.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentTypeBackup;
import cn.soa.entity.EquipmentTypeForExcel;

/**
 * 设备台账导入备份持久层
 * @author Jiang, Hang
 * @since 2019-08-01
 */
@Mapper
public interface EquipmentTypeBackupMapper {
	
	/**
	 * 添加设备台账导入备份信息
	 * @param backup 备份对象信息
	 * @return 受影响行数
	 */
	Integer insertBackupInfo(EquipmentTypeBackup backup);
	
	/**
	 * 条件查询设备台账导入备份信息
	 * @param bname 设备类型
	 * @param startDate 开始时间
	 * @param endDate  结束时间
	 * @param bperson  操作人 
	 * @return 设备台账导入备份信息列表
	 */
	List<EquipmentTypeBackup> findByCondition(
			@Param("bname") String bname,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("bperson") String bperson,
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	/**
	 * 根据bid查询设备台账导入备份信息
	 * @param bid 主键id
	 * @return 设备台账导入备份信息
	 */
	EquipmentTypeBackup findByBid(String bid);
	
	/**
	 * 统计条件查询备份信息总数
	 */
	Integer countByCondition(
			@Param("bname") String bname,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("bperson") String bperson);
	
	/**
	 * 根据bid删除设备台账导入备份信息
	 * @param bid 主键id
	 * @return 受影响行数
	 */
	Integer deleteByBid(String bid);
	
	/**
	 * 根据设备位号还原设备备份数据
	 * @param data 设备备份数据
	 * @return 受影响行数
	 */
	Integer updateBackup(EquipmentTypeForExcel data);
	
	/**
	 * 插入设备数据
	 * @param data 设备数据
	 * @return 受影响行数
	 */
	Integer insertBackup(EquipmentTypeForExcel data);
}
