package cn.soa.service.intel.lubrication;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;

/**
 * 设备润滑换油 业务层
 * @author Luo Guimao
 * 
 */
public interface EquipmentLubricationSI {
	
	/**
	 * 新增换油设备
	 * @return
	 */

	Integer addLub(LubricateEquipmentPlace lubricateEquipmentPlace);
	
	/**
	 * 按条件分页查询换油设备
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  换油设备数据列表
	 */
	ResultJsonForTable<List<LubricateEquipment>> findEquipLubricationByPage(
			LubricateEquipment equip, Integer page, Integer limit);
	
	
	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 润滑设备lid
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  设备换油记录数据列表
	 */
	ResultJsonForTable<List<LubricateEquipmentRecord>> findEquipLubricationRecordByLid(
			String lid, Integer page, Integer limit);
	
	/**
	 * 临时换油日期跟踪
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param page 第几页
	 * @param limit 每页条数
	 * return 
	 */
	ResultJsonForTable<List<LubricateEquipmentPlace>> findEquipLubricationTrace(
			String positionnum, String tname, Integer page, Integer limit);
}
