package cn.soa.dao.spareparts;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SpRecord;
import cn.soa.entity.spareparts.SparePart;

@Mapper
public interface SparePartMapper {
	int insert(SparePart record);

	int insertSelective(SparePart record);

	/**
	 * 根据条件查询数据
	 * 
	 * @param alarm
	 * @return
	 */
	Page<SparePart> findByCondition(QueryCondition condition);

	/**
	 * 批量删除数据
	 * 
	 * @param spareParts
	 * @return
	 */
	Integer delAsBatch(List<SparePart> spareParts);

	/**
	 * 
	 * 更新数据（动态）
	 * 
	 * @param sparePart
	 * @return
	 */
	Integer updateSelective(SparePart sparePart);


	/**
	 * 根据设备出入库记录表增加库存
	 * @param spRecord
	 */
	Integer addNumBySpRecord(SpRecord spRecord);

	/**
	 * 根据设备出入库记录表减少库存
	 * @param spRecord
	 */
	Integer subNumBySpRecord(SpRecord spRecord);

}