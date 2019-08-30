package cn.soa.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import cn.soa.entity.EquipmentMoveRunningTime;

@Mapper
public interface EquipmentMoveRunningCountTimeMapper {
	public List<Map<String,Object>> findChangeOrFixDate(@Param("position")String position,@Param("even")String event, @Param("time")String time);
	List<Map<String, Object>> countEquipmentRunningOrgTime();
	//查询需要统计的设备
	List<EquipmentMoveRunningTime> findAll();
	
}
