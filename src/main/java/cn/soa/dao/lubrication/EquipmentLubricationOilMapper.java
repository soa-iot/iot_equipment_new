package cn.soa.dao.lubrication;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;

/**
 * 设备大事件 持久层
 * @author Luo Guimao
 * Storage 入库
 */
@Mapper
public interface EquipmentLubricationOilMapper {
	
	/**
	 * 设备润滑新增油品
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	Integer insertOil(@Param("equip") EquipmentLubricationOil equipmentLubricationOil);
	
	/**
	 * 设备润滑新增油品出入库记录
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	Integer insertRecord(@Param("eqor") EquipmentOilRecord equipmentOilRecord);
	
	/**
	 * 查询所有油品
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	List<EquipmentOilRecordVO> findOilAll(Integer page);
}
