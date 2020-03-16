package cn.soa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentDisplayInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipmentDisplayInfo record);

    int insertSelective(EquipmentDisplayInfo record);

    EquipmentDisplayInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipmentDisplayInfo record);

    int updateByPrimaryKey(EquipmentDisplayInfo record);
    
    /**
	 * 根据条件获取数据
	 * 
	 * @param condition
	 * @return
	 */
	List<EquipmentDisplayInfo> selectByCondition(QueryCondition condition);

	/**
	 * 根据条件获取表单数据
	 * 
	 * @param condition
	 * @return
	 */
	List<EquipmentDisplayInfo> selectFormInfoByCondition(QueryCondition condition);
	
	/**
	 * 根据条件获取classNum的最大值
	 * @param condition
	 * @return
	 */
	Map<String, String> selectMaxClassNum(String equTypeId);

	/**
	 * 根据条件获取可搜索表单信息
	 * @param condition
	 * @return
	 */
	List<EquipmentDisplayInfo> selectSearchFormInfo(QueryCondition condition);

	/**
	 * 查询所有数据
	 * @return
	 */
	List<EquipmentDisplayInfo> findAll();
}