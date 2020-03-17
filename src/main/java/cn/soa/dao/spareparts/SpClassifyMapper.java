package cn.soa.dao.spareparts;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.SpClassify;

@Mapper
public interface SpClassifyMapper {
    int insert(SpClassify record);

    int insertSelective(SpClassify record);

	/**
	 * @return
	 */
	List<TreeObject> findAsTree();

	/**
	 * 批量删除数据
	 * @param spClassifies
	 * @return
	 */
	Integer delBatch(List<SpClassify> spClassifies);

	/**
	 * 动态更新数据
	 * @param spClassify
	 * @return
	 */
	Integer updateSelective(SpClassify spClassify);
}