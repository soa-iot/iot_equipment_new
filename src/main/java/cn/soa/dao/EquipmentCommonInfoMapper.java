package cn.soa.dao;

import com.github.pagehelper.Page;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentCommonInfoMapper {
	int deleteByPrimaryKey(String equId);

	int insert(EquipmentCommonInfo record);

	int insertSelective(EquipmentCommonInfo record);

	EquipmentCommonInfo selectByPrimaryKey(String equId);

	int updateByPrimaryKeySelective(EquipmentCommonInfo record);

	int updateByPrimaryKey(EquipmentCommonInfo record);

	/**
	 * 
	 * 根据条件查询设备信息
	 * 
	 * @param condition
	 * @return
	 */
	Page<EquipmentCommonInfo> selectByCondition(@Param("condition")QueryCondition condition);
	
	
	/**
	 * 根据id批量删除设备数据
	 */
	int delRecordByIds(List<EquipmentCommonInfo> equipmentCommonInfos);
	
	/**
	 * 插入数据到设备-设备类型关系表
	 * @param record
	 * @return
	 */
	int insertEquTypeR(EquipmentCommonInfo record);
	
	/**
	 * 恢复设备数据
	 * @param condition
	 * @return
	 */
	int recoveryEquInfo(QueryCondition condition);

	/**
	 * @param equTypeId
	 */
	int deleteByEquTypeId(String equTypeId);

	/**
	 * 动态匹配筛选条件
	 * @param equipmentCommonInfo 
	 * @return
	 */
	Page<EquipmentCommonInfo> findBySelective(EquipmentCommonInfo equipmentCommonInfo);
	

}