package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentProperties;

@Mapper
public interface EquipmentPropertiesMapper {
	int deleteByPrimaryKey(String id);

	int insert(EquipmentProperties record);

	int insertSelective(EquipmentProperties record);

	EquipmentProperties selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(EquipmentProperties record);

	int updateByPrimaryKey(EquipmentProperties record);

	/**
	 * 根据equId和proNameEn修改 数据
	 * 
	 * @param record
	 * @return
	 */
	int updateSelective(EquipmentProperties record);

	/**
	 * 根据equId批量删除数据
	 * 
	 * @param equipmentCommonInfos
	 * @return
	 */
	int delByEquIds(List<EquipmentCommonInfo> equipmentCommonInfos);
}