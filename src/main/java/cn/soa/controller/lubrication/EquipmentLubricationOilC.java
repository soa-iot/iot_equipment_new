package cn.soa.controller.lubrication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResultJson;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.service.impl.lubrication.EquipmentLubricationOilS;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备润滑控制层
 * @author Luo Guimao
 * @since 2019-08-01
 */

@RestController
@Slf4j
@RequestMapping("/equipmentoil")
public class EquipmentLubricationOilC {
	private EquipmentLubricationOilS equipmentLubricationOilS;
	
	/**
	 * 新增油品
	 * @return
	 */
	@RequestMapping("/addoil")
	public ResultJson<Integer> addOil(EquipmentLubricationOil equipmentLubricationOil,String userid,String rnote) {
		
		log.info("===============================新增油品："+equipmentLubricationOil.toString());
		Integer row = equipmentLubricationOilS.addOil(equipmentLubricationOil,userid,rnote);
		log.info("===============================新增油品数量："+row);
		if (row > 0 ) {
			return new ResultJson<Integer>(0, "新增油品成功");
		}else {
			return new ResultJson<Integer>(1, "新增油品失败");
		}
	}
}
