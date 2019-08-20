package cn.soa.service.intel.lubrication;

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
	
	List<EquipmentOilRecordVO> queryOilAll(Integer page);
}
