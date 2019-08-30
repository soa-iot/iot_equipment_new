package cn.soa.service.intel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJsonForTable;

/**
 * 动设备(运行时间)管理 业务层
 * @author Jiang, Hang
 * @since 2019-07-18
 */
public interface EquipmentMoveRunningTimeSI {
	
	/**
	 * 分页查找动设备信息
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return 动设备信息列表
	 */
	ResultJsonForTable<List<EquipmentMoveRunningTime>> findByPage(
			@Param("equip") EquipmentMoveRunningTime equip, 
			@Param("page") Integer page, 
			@Param("limit") Integer limit);

	/**   
	 * @Title: getRunningEquipment   
	 * @Description:   获取需要监控运行时间的动设备   
	 * @return: Map<String,String>        
	 */  
	List<EquipmentMoveRunningTime> getRunningEquipment();

	/**   
	 * @Title: getRunningEquipmentNum   
	 * @Description: 获取所有动设备的代号   
	 * @return: Map<String,String>        
	 */  
	Map<String, Object> getRunningEquipmentNum();
	
	/**
	 * 添加动设备数据
	 * @param equip 动设备信息
	 * @return 是否添加成功
	 */
	String addOne(EquipmentMoveRunningTime equip);

	/**   
	 * @Title: getEquipPositionAndNumberAndDCS   
	 * @Description:  查询动设备位号对应的dcs位号和分代号  
	 * @return: Map<String,Map<String,Object>>        
	 */  
	Map<String, Map<String, Object>> getEquipPositionAndNumberAndDCS();

	/**   
	 * @Title: getEquipmentRunningS   
	 * @Description:分页查询设备运行时间     
	 * @return: List<Map<String,Object>>        
	 */  
	List<Map<String, Object>> getEquipmentRunningS(Map<String, Object> m, String page, String size, String startTime,
			String endTime);

	/**   
	 * @Title: getEquipmentRunningAllS   
	 * @Description: 查找所有设备运行的设备    
	 * @return: List<EquipmentMoveRunningTime>        
	 */  
	List<EquipmentMoveRunningTime> getEquipmentRunningAllS();

	/**   
	 * @Title: getEquipmentRunningCount   
	 * @Description: 获取设备运行统计的全部设备数量    
	 * @return: Integer        
	 */  
	Integer getEquipmentRunningCount();
}
