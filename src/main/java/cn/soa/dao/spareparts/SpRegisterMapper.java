package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SpRegister;

@Mapper
public interface SpRegisterMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpRegister record);

    int insertSelective(SpRegister record);

    SpRegister selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpRegister record);

    int updateByPrimaryKey(SpRegister record);

	/**
	 * 根据条件查询数据
	 * @param condition
	 * @return
	 */
	Page<SpRegister> selectByCondition(QueryCondition condition);
	
}