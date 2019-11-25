package cn.soa.service.intel.lubrication;

import java.util.Date;
import java.util.List;

import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;

/**
 * 设备润滑 业务层
 * @author Luo Guimao
 * 
 */
public interface EquipmentLubricationOilSI {
	
	/**
	 * 新增油品
	 * @return
	 */
	Integer addOil(EquipmentLubricationOil equipmentLubricationOil,String userid,String rnote);
	
	/**
	 * 根据油品库存条件查询油品
	 * @param equipmentLubricationOil
	 * @return
	 */
	List<EquipmentLubricationOil> findOilbyConditions(EquipmentLubricationOil equipmentLubricationOil);
	/**
	 * 分页查询出入库油品记录
	 * @param page
	 * @param limit
	 * @return
	 */
	List<EquipmentOilRecordVO> queryOilAll(Integer minSize,Integer maxSize,String oid, String startTime,String endTime);
	
	/**
	 * 查询油品出入库记录总数量
	 * @return
	 */
	Integer countRecord(String oid, String startTime,String endTime);
	
	/**
	 * 查询所有油品
	 * @param page
	 * @param limit
	 * @return
	 */
	List<EquipmentLubricationOil> queryOilAllStock(Integer minSize, Integer maxSize);
	
	/**
	 * 查询所有油品数量
	 * @return
	 */
	Integer countStock();
	
	/**
	 * 油品入库
	 * @param oname
	 * @param ramount
	 * @param rnote
	 * @param userid
	 * @return
	 */
	Integer oilStock(String oname,String ramount,String rnote,String userid,String otype,String calcType);
	
	/**
	 * 更新/删除油品
	 * @param equipmentLubricationOil
	 * @return
	 */
	Integer updateOil(EquipmentLubricationOil equipmentLubricationOil);
}
