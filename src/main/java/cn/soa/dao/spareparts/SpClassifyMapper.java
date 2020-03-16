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
}