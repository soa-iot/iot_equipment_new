package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.spareparts.SpRecord;

@Mapper
public interface SpRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpRecord record);

    int insertSelective(SpRecord record);

    SpRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpRecord record);

    int updateByPrimaryKey(SpRecord record);
}