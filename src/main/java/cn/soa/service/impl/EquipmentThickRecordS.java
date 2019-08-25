package cn.soa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.dao.EquipmentThickRecordMapper;
import cn.soa.entity.EquipmentThickManagement;
import cn.soa.entity.EquipmentThickRecord;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentThickRecordSI;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备测厚记录业务层
 * @author Jiang, Hang
 * @since 2019-08-22
 */
@Service
@Slf4j
public class EquipmentThickRecordS implements EquipmentThickRecordSI {

	@Autowired
	private EquipmentThickRecordMapper recordMapper;

	/**
	 * 根据条件查找出设备测厚记录数据
	 * @param  equipRecord 设备测厚查询条件
	 * @return 设备测厚记录列表
	 */
	@Override
	public ResultJsonForTable<List<EquipmentThickRecord>> findEquipRecord(EquipmentThickRecord record,
			String startDate, String endDate, Integer page, Integer limit) {
		log.info("------开始条件查询测厚记录数据-------");
		//1. 统计条件查询出数据的总条数
		Integer total = recordMapper.countEquipRecord(record, startDate, endDate);
		if(total == null || total == 0) {
			log.info("------查询失败，统计总条数为0-------");
			return new ResultJsonForTable<List<EquipmentThickRecord>>(0, "查询失败，统计总条数为0", 0, null);
		}
		//2. 条件查询设备测厚数据
		List<EquipmentThickRecord> result = recordMapper.findEquipRecord(record, startDate, endDate, page, limit);
		if(result == null) {
			log.info("------查询失败，查询结果为null-------");
			return new ResultJsonForTable<List<EquipmentThickRecord>>(0, "查询失败，查询结果为null", 0, null);
		}
		log.info("------查询测厚记录成功，总条数为{}-------", total);
		return new ResultJsonForTable<List<EquipmentThickRecord>>(0, "查询测厚记录成功", total, result);
	}

	@Override
	public String addEquipThick(EquipmentThickManagement equipThick) {
		log.info("------开始插入设备测厚数据------");

		try {
			//1.检查设备id是否已存在
			Integer result = recordMapper.findByEid(equipThick.getEid());
			if(result != 0) {
				log.info("------设备测厚数据已存在:{}------", equipThick.getEid());
				return "设备测厚数据已存在";
			}

			recordMapper.insertEquipThick(equipThick);
		}catch (Exception e) {
			log.info("------插入设备测厚数据失败------");
			log.info(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		log.info("------插入设备测厚数据成功 tid:{}------", equipThick.getTid());
		return "插入设备测厚数据成功";
	}

	@Override
	public ResultJsonForTable<List<EquipmentThickManagement>> findEquipThick(EquipmentThickManagement equipThick,
			Integer page, Integer limit) {
		log.info("------开始查询设备测厚数据------");

		//1. 统计条件查询出数据的总条数
		Integer total = recordMapper.countEquipThick(equipThick);
		if(total == null || total == 0) {
			log.info("------查询失败，统计总条数为0-------");
			return new ResultJsonForTable<List<EquipmentThickManagement>>(0, "查询失败，统计总条数为0", 0, null);
		}
		//2. 条件查询设备测厚数据
		List<EquipmentThickManagement> result = recordMapper.findEquipThick(equipThick, page, limit);
		if(result == null) {
			log.info("------查询失败，查询结果为null-------");
			return new ResultJsonForTable<List<EquipmentThickManagement>>(0, "查询失败，查询结果为null", 0, null);
		}
		log.info("------查询测厚设备数据成功，总条数为{}-------", total);
		return new ResultJsonForTable<List<EquipmentThickManagement>>(0, "查询测厚设备数据成功", total, result);
	}

}
