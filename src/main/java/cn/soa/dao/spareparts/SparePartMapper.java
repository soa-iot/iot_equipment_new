package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.spareparts.SparePart;

@Mapper
public interface SparePartMapper {
    int insert(SparePart record);

    int insertSelective(SparePart record);
}