package cn.soa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentInfoMapper;
import cn.soa.entity.EquipmentInfo;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;
import cn.soa.service.intel.EquipmentInfoSI;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EquipmentInfoS implements EquipmentInfoSI {
	
	@Autowired
	private EquipmentInfoMapper equipmentInfoMapper;
	
	/**   
	 * @Title: getEquipmentInfo   
	 * @Description: 根据条件查找出设备信息数据
	 * @return: List<EquipmentInfo> 返回设备数据列表   
	 */
	@Override
	public ResultJsonForTable<List<EquipmentInfo>> getEquipmentInfo(EquipmentInfo info, Integer page, Integer limit) {
		log.info("------开始根据条件查找出设备信息数据------");
		//先统计总数量
		Integer total = equipmentInfoMapper.countEquipmentInfo(info);
		System.err.println("total: "+total);
		if(total == null || total == 0) {
			log.info("------根据条件查找出设备信息数据：count={}------", total);
			return new ResultJsonForTable<List<EquipmentInfo>>(0, "查询结束", 0, null);
		}
		//分页查询
		List<EquipmentInfo> result = equipmentInfoMapper.findEquInfo(info, page, limit);
		log.info("------根据根据条件查找出设备信息数据，查询条数为： count={}------", total);
		return new ResultJsonForTable<List<EquipmentInfo>>(0, "查询成功", total, result);
	}
}
