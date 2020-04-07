package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentType;

@Mapper
public interface EquipmentTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipmentType record);

    int insertSelective(EquipmentType record);

    EquipmentType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipmentType record);

    int updateByPrimaryKey(EquipmentType record);
    
    /**
     * 查询所有设备分类信息（包含子级）
     * @return
     */
    List<EquipmentType> selectAllWithChidren();

	/**
	 * 根据id获取其下级所有的数据
	 * @param equTypeId
	 * @return
	 */
	List<EquipmentType> selectChildrenById(String equTypeId);
}