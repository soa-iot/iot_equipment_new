package cn.soa.dao.spareparts;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.spareparts.ClassifySpRelation;

@Mapper
public interface ClassifySpRelationMapper {
    int insert(ClassifySpRelation record);

    int insertSelective(ClassifySpRelation record);

	/**
	 * 
	 * 批量删除数据
	 * @param classifySpRelations
	 * @return
	 */
	Integer delBatch(List<ClassifySpRelation> classifySpRelations);

	/**
	 * 根据备件id和备件分类id批量删除数据
	 * @param classifySpRelations
	 * @return
	 */
	Integer delBatchByCidAndSpId(List<ClassifySpRelation> classifySpRelations);
}