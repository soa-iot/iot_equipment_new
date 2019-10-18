package cn.soa.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentPropertiesBack;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentPropertiesBackMapper {
	int deleteByPrimaryKey(String id);

	int insert(EquipmentPropertiesBack record);

	int insertSelective(EquipmentPropertiesBack record);

	EquipmentPropertiesBack selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(EquipmentPropertiesBack record);

	int updateByPrimaryKey(EquipmentPropertiesBack record);

	/**
	 * 根据条件插入数据
	 * 
	 * @param condition
	 * @return
	 */
	int insertByCondition(QueryCondition condition);
}