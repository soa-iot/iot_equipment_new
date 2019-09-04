package cn.soa.dao;

import com.github.pagehelper.Page;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquipmentCommonInfoMapper {
	int deleteByPrimaryKey(String equId);

	int insert(EquipmentCommonInfo record);

	int insertSelective(EquipmentCommonInfo record);

	EquipmentCommonInfo selectByPrimaryKey(String equId);

	int updateByPrimaryKeySelective(EquipmentCommonInfo record);

	int updateByPrimaryKey(EquipmentCommonInfo record);

	/**
	 * 
	 * 根据条件查询设备信息
	 * 
	 * @param condition
	 * @return
	 */
	Page<EquipmentCommonInfo> selectByCondition(QueryCondition condition);
	
	
	/**
	 * 根据id批量删除设备数据
	 */
	int delRecordByIds(List<EquipmentCommonInfo> equipmentCommonInfos);
	

}