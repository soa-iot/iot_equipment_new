package cn.soa.service.intel.lubrication;

import cn.soa.entity.lubrication.LubricateEquipmentPlace;

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
}
