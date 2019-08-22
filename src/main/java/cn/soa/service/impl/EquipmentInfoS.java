package cn.soa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentInfoMapper;
import cn.soa.entity.EquipmentInfo;
import cn.soa.service.intel.EquipmentInfoSI;


@Service
public class EquipmentInfoS implements EquipmentInfoSI {
	
	@Autowired
	private EquipmentInfoMapper equipmentInfoMapper;
	
	/**   
	 * @Title: getEquipmentInfo   
	 * @Description: 根据条件查找出设备信息数据
	 * @return: List<EquipmentInfo> 返回设备数据列表   
	 */
	@Override
	public List<EquipmentInfo> getEquipmentInfo(EquipmentInfo info, Integer page, Integer limit) {
		
		return equipmentInfoMapper.findEquInfo(info, page, limit);
	}
	
	/**   
	 * @Title: countEquipmentInfo   
	 * @Description: 根据条件查统计设备信息条数
	 * @return: List<EquipmentInfo> 返回条数   
	 */
	@Override
	public Integer countEquipmentInfo(EquipmentInfo info) {
		
		return equipmentInfoMapper.countEquipmentInfo(info);
	}

}
