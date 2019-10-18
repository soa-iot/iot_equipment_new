package cn.soa.dao;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.EquipmentSpare;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentSpareMapper {
    int deleteByPrimaryKey(String spareId);

    int insert(EquipmentSpare record);

    int insertSelective(EquipmentSpare record);

    EquipmentSpare selectByPrimaryKey(String spareId);

    int updateByPrimaryKeySelective(EquipmentSpare record);

    int updateByPrimaryKey(EquipmentSpare record);

	/**
	 * @param condition
	 * @return
	 */
	Page<EquipmentSpare> selectByCondition(QueryCondition condition);
}