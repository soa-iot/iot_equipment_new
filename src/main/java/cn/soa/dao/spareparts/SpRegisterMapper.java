package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.spareparts.SpRegister;

@Mapper
public interface SpRegisterMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpRegister record);

    int insertSelective(SpRegister record);

    SpRegister selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpRegister record);

    int updateByPrimaryKey(SpRegister record);
}