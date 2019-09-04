package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentDisplayInfoMapper {
	int deleteByPrimaryKey(String id);

	int insert(EquipmentDisplayInfo record);

	int insertSelective(EquipmentDisplayInfo record);

	EquipmentDisplayInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(EquipmentDisplayInfo record);

	int updateByPrimaryKey(EquipmentDisplayInfo record);

	/**
	 * 根据条件获取数据
	 * 
	 * @param condition
	 * @return
	 */
	List<EquipmentDisplayInfo> selectByCondition(QueryCondition condition);

	/**
	 * 根据条件获取表单数据
	 * 
	 * @param condition
	 * @return
	 */
	List<EquipmentDisplayInfo> selectFormInfoByCondition(QueryCondition condition);
}