package cn.soa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentMoveRunningTimeMapper;
import cn.soa.entity.EquipmentMoveInfo;
import cn.soa.service.intel.EquipmentMoveMaintenanceSI;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EquipmentMoveMaintenanceS implements EquipmentMoveMaintenanceSI{
	
	@Autowired
	private EquipmentMoveRunningTimeMapper equipMoveMapper;

	@Override
	public List<EquipmentMoveInfo> getEquipMoveInfo(String positionNum, String equipName, String startDate,
			String endDate, Integer page, Integer limit) {
		log.info("-----分页查找动设备检维修信息-----");
		
		try {
			List<EquipmentMoveInfo> result = equipMoveMapper.selectEquipMoveInfo(positionNum, equipName, startDate, endDate, page, limit);
			
			if(result == null) {
				result = new ArrayList<EquipmentMoveInfo>();
			}
			log.info("-----分页查找动设备检维修信息成功-----");
			return result;
		}catch (Exception e) {
			log.info("-----分页查找动设备检维修信息发生错误-----");
			log.info("{}", e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean insertEquipMoveInfo(EquipmentMoveInfo info) {
		log.info("-----添加动设备检维修信息-----");
		
		try {
			//info.setMaintenanceTime(new Date());
			Integer rows = equipMoveMapper.insertEquipMoveInfo(info);
			
			if(rows == null || rows == 0) {
				log.info("-----添加动设备检维修信息失败-----");
				return false;
			}
			log.info("-----添加动设备检维修信息成功-----");
			return true;
		}catch (Exception e) {
			log.info("-----添加动设备检维修信息发生错误-----");
			log.info("{}", e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteEquipMoveInfo(String mid) {
		log.info("-----删除动设备检维修信息-----");
		
		try {
			Integer rows = equipMoveMapper.deleteEquipMoveInfo(mid);
			
			if(rows == null || rows == 0) {
				log.info("-----删除动设备检维修信息失败-----");
				return false;
			}
			log.info("-----删除动设备检维修信息成功-----");
			return true;
		}catch (Exception e) {
			log.info("-----删除动设备检维修信息发生错误-----");
			log.info("{}", e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateEquipMoveInfo(EquipmentMoveInfo info) {
		log.info("-----更新动设备检维修信息-----");
		
		try {
			Integer rows = equipMoveMapper.updateEquipMoveInfo(info);
			
			if(rows == null || rows == 0) {
				log.info("-----更新动设备检维修信息失败-----");
				return false;
			}
			log.info("-----更新动设备检维修信息成功-----");
			return true;
		}catch (Exception e) {
			log.info("-----更新动设备检维修信息发生错误-----");
			log.info("{}", e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Integer countEquipMoveInfo(String positionNum, String equipName, String startDate, String endDate) {
		log.info("-----统计动设备检维修信息条数-----");
		
		try {
			Integer row = equipMoveMapper.countEquipMoveInfo(positionNum, equipName, startDate, endDate);

			log.info("-----统计动设备检维修信息条数成功-----");
			return row;
		}catch (Exception e) {
			log.info("-----统计动设备检维修信息条数发生错误-----");
			log.info("{}", e);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据主键id查找动设备检维修信息
	 * @param mid - 主键id
	 * @return EquipmentMoveInfo - 动设备检维修信息
	 */
	@Override
	public List<EquipmentMoveInfo> getEquipMoveInfo(String[] mid) {
		log.info("-----根据主键id查找动设备检维修信息-----");
		List<EquipmentMoveInfo> list = new ArrayList<>();
		try {
			for(String str : mid) {
				EquipmentMoveInfo info = equipMoveMapper.selectEquipMoveInfoByMid(str);
				if(info != null) {
					list.add(info);
				}
			}

			log.info("-----根据主键id查找动设备检维修信息成功-----");
			return list;
		}catch (Exception e) {
			log.info("-----根据主键id查找动设备检维修信息发生错误-----");
			log.info("{}", e);
			e.printStackTrace();
			return null;
		}
	}

}
