package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.spareparts.classifySpRelation;

@Mapper
public interface classifySpRelationMapper {
    int insert(classifySpRelation record);

    int insertSelective(classifySpRelation record);
}