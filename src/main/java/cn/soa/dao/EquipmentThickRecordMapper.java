package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentThickManagement;
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
	
	/**
	 * 根据设备id和年份查找出设备测厚记录数据
	 * @param  eid 设备id
	 * @param  measuretime 测量时间
	 * @param  startYear 开始年份
	 * @param  endYear 结束年份
	 * @return 设备测厚记录列表
	 */
	List<EquipmentThickRecord> findEquipRecordByEidAndYear(
			@Param("eid") String eid, 
			@Param("measuretime") String measuretime,
			@Param("startYear") String startYear, 
			@Param("endYear") String endYear);
	
	/**
	 * 根据设备位号精确查找出设备测厚数据
	 * @param  positionnum 设备位号
	 * @return 设备测厚数据
	 */
	EquipmentThickManagement findEquipThickByPositionnum(@Param("positionnum") String positionnum);
	
	/**
	 * 新增设备测厚数据
	 * @param  equipThick 设备数据
	 * @return 生效行数
	 */
	Integer insertEquipThick(EquipmentThickManagement equipThick);
	
	/**
	 * 根据设备ID查找出设备测厚数据
	 * @param  eid ID
	 * @return 查询出的行数
	 */
	Integer findByEid(String eid);
	
	/**
	 * 根据条件查找出设备测厚数据
	 * @param  equipThick 设备测厚查询条件
	 * @return 设备测厚列表
	 */
	List<EquipmentThickManagement> findEquipThick(
			@Param("equipThick") EquipmentThickManagement equipThick,  
			@Param("page") Integer page, 
			@Param("limit") Integer limit);
	
	/**
	 * 统计条件查询设备测厚记录总条数
	 * @param  equipThick 设备测厚查询条件
	 * @return 设备测厚总条数
	 */
	Integer countEquipThick(@Param("equipThick") EquipmentThickManagement equipThick); 

}
