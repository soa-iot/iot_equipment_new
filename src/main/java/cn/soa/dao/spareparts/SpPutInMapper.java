package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.spareparts.SpPutIn;

@Mapper
public interface SpPutInMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpPutIn record);

    int insertSelective(SpPutIn record);

    SpPutIn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpPutIn record);

    int updateByPrimaryKey(SpPutIn record);
}