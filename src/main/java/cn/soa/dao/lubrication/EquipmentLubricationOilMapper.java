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
	 * 根据油品库存条件查询油品
	 * @param equipmentLubricationOil
	 * @return
	 */
	public List<EquipmentLubricationOil> findOilbyConditions(EquipmentLubricationOil equipmentLubricationOil);
	
	/**
	 * 设备润滑新增油品出入库记录
	 * @author Luo Guimao
	 * @return
	 */
	Integer insertRecord(@Param("eqor") EquipmentOilRecord equipmentOilRecord);
	
	/**
	 * 查询所有油品
	 * @author Luo Guimao
	 * @return
	 */
	List<EquipmentOilRecordVO> findOilAll(
			@Param("minSize")Integer minSize,
			@Param("maxSize")Integer maxSize,
			@Param("oid")String oid,
			@Param("startTime")String startTime,
			@Param("endTime")String endTime);
	
	/**
	 * 查询油品出入库总数量
	 * @return
	 */
	Integer countRecord(String oid, String startTime,String endTime);

	/**
	 * 查询所有油品数量
	 * @return
	 */
	Integer countStock();

	/**
	 * 查询所有油品
	 * @param page
	 * @param limit
	 * @return
	 */
	List<EquipmentLubricationOil> queryOilAllStock(Integer minSize, Integer maxSize);
	
	/**
	 * 油品入库
	 * @param ramount
	 * @param oid
	 * @return
	 */
	Integer updateStock(Integer ramount,String oid);
}
