package cn.soa.dao.spareparts;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SpPutIn;

@Mapper
public interface SpPutInMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpPutIn record);

    int insertSelective(SpPutIn record);

    SpPutIn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpPutIn record);

    int updateByPrimaryKey(SpPutIn record);

	/**
	 * 根据条件查询数据
	 * @param condition
	 * @return
	 */
	Page<SpPutIn> findByCondition(QueryCondition condition);

	/**
	 * 根据申请单号更新数据
	 * @param spPutIn
	 */
	Integer updateByRequestCode(SpPutIn spPutIn);
}