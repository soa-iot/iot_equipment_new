package cn.soa.service.intel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EquipmentMoveRunningBYyearSI {
	//统计设备每个月的运行时间
	public List<Map<String ,Object>>countEquipmentRunningEveryMonth(String Time,String equipment_number,Integer page,Integer limit);
	//统计每个设备的初始化时间
	public List<Map<String ,Object>>countEquipmentRunningOrgTime();
	//寻找出设备大修或者更换的时间
	String findChangeOrFixDate(String position,String event,String Time);
	//统计某个时间点设备的运行时间
	public List<Map<String ,Object>>countEquipmentRunningEveryYear(String time,String equipment_number,Integer page,Integer limit);
	
}
