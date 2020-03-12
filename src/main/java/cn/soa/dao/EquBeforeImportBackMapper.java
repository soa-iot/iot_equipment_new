package cn.soa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.EquBeforeImportBack;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquBeforeImportBackMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquBeforeImportBack record);

    int insertSelective(EquBeforeImportBack record);

    EquBeforeImportBack selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquBeforeImportBack record);

    int updateByPrimaryKey(EquBeforeImportBack record);

	/**
	 * @param condition
	 * @return
	 */
    Page<EquBeforeImportBack> selectByCondition(QueryCondition condition);
}