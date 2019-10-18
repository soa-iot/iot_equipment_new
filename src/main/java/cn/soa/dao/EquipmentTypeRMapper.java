package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentTypeR;

@Mapper
public interface EquipmentTypeRMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipmentTypeR record);

    int insertSelective(EquipmentTypeR record);

    EquipmentTypeR selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipmentTypeR record);

    int updateByPrimaryKey(EquipmentTypeR record);
    
    
    /**
     * 批量插入数据
     * @param records
     * @return
     */
    int addRecores(List<EquipmentTypeR> records);
}