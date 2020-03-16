package cn.soa.dao.spareparts;

import cn.soa.entity.spareparts.EqOrSpRelation;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EqOrSpRelationMapper {
    int deleteByPrimaryKey(BigDecimal rId);

    int insert(EqOrSpRelation record);

    int insertSelective(EqOrSpRelation record);

    EqOrSpRelation selectByPrimaryKey(BigDecimal rId);

    int updateByPrimaryKeySelective(EqOrSpRelation record);

    int updateByPrimaryKey(EqOrSpRelation record);

	/**
	 * 批量删除数据
	 * @param eqOrSpRelations
	 * @return
	 */
	Integer delByIdBatch(List<EqOrSpRelation> eqOrSpRelations);

	/**
	 * 
	 * 根据设备id查询数据
	 * @param eqId
	 * @return
	 */
	List<EqOrSpRelation> findByEqId(String eqId);
	
}