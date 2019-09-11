package cn.soa.service.intel;

import java.util.List;

import cn.soa.entity.EquipmentInfo;
import cn.soa.entity.ResultJsonForTable;

public interface EquipmentInfoSI {
	
	/**   
	 * @Title: getEquipmentInfo   
	 * @Description: 根据条件查找出设备信息数据
	 * @return: List<EquipmentInfo> 返回设备数据列表   
	 */
	public ResultJsonForTable<List<EquipmentInfo>> getEquipmentInfo(EquipmentInfo info, Integer page, Integer limit);
	
}
