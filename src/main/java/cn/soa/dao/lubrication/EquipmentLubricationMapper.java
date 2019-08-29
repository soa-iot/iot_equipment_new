package cn.soa.dao.lubrication;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;

/**
 * 设备大事件 持久层
 * @author Luo Guimao
 * Storage 入库
 */
@Mapper
public interface EquipmentLubricationMapper {
	
	/**
	 * 设备润滑换油新增
	 * @author Luo Guimao
	 * @param LubricateEquipment
	 */
	Integer insertLubEqui(@Param("lubequi")LubricateEquipment lubricateEquipment);
	
	/**
	 * 新增换油部位
	 * @param LubricateEquipmentPlace
	 */
	Integer insertLubPlace(@Param("lubequipl")LubricateEquipmentPlace lubricateEquipmentPlace);
	
	/**
	 * 新增换油记录
	 * @param LubricateEquipmentRecord
	 */
	Integer insertLubRecord(LubricateEquipmentRecord lubricateEquipmentRecord);
	
	/**
	 * 查询换油设备
	 * @param lubricateEquipment
	 * @return
	 */
	List<LubricateEquipment> findLubEqui(LubricateEquipment lubricateEquipment);
	
	/**
	 * 查询换油部位
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	List<LubricateEquipmentPlace> findLubPlace(LubricateEquipmentPlace lubricateEquipmentPlace);
	
	/**
	 *查询换油记录 
	 * @param lubricateEquipmentRecord
	 * @return
	 */
	List<LubricateEquipmentRecord> findLubRecord(LubricateEquipmentRecord lubricateEquipmentRecord);
	
}
