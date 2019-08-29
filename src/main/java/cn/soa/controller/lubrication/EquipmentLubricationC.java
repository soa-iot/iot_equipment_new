package cn.soa.controller.lubrication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResultJson;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.service.intel.lubrication.EquipmentLubricationSI;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备润滑控制层
 * @author Luo Guimao
 * @since 2019-08-01
 */

@RestController
@Slf4j
@RequestMapping("/lubrication")
public class EquipmentLubricationC {
	
	@Autowired
	private EquipmentLubricationSI equipmentLubricationSI;
	
	/**
	 * 新增换油设备
	 * @return
	 */
	@RequestMapping("/addlub")
	public ResultJson<Integer> addLub(LubricateEquipmentPlace lubricateEquipmentPlace) {
		//Integer row=0;
		log.info("===============================新增润滑设备："+lubricateEquipmentPlace);
		log.info("===============================新增润滑设备："+lubricateEquipmentPlace.getLnamekey()+";"+lubricateEquipmentPlace.getLname());
		Integer row = equipmentLubricationSI.addLub(lubricateEquipmentPlace);
		log.info("===============================新增油品数量："+row);
		if (row > 0 ) {
			return new ResultJson<Integer>(0, "新增油品成功");
		}else {
			return new ResultJson<Integer>(1, "新增油品失败");
		}
	}
	
}
