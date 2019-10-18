package cn.soa.dao;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.EquipmentCommonInfoBack;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentCommonInfoBackMapper {
    int deleteByPrimaryKey(String backId);

    int insert(EquipmentCommonInfoBack record);

    int insertSelective(EquipmentCommonInfoBack record);

    EquipmentCommonInfoBack selectByPrimaryKey(String backId);

    int updateByPrimaryKeySelective(EquipmentCommonInfoBack record);

    int updateByPrimaryKey(EquipmentCommonInfoBack record);
    
    
    /**
     * 根据条件插入备份记录
     * @param condition
     * @return
     */
    int insertByCondition(QueryCondition condition);

	/**
	 * 根据条件查询备份记录
	 * @param condition
	 * @return
	 */
	Page<EquipmentCommonInfoBack> selectByCondition(QueryCondition condition);
}