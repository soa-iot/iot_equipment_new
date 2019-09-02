package cn.soa.controller.lubrication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;
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
	
	/**
	 * 按条件分页查询换油设备
	 * @param equip 查询条件
	 * @return
	 */
	@GetMapping("/management/query")
	public ResultJsonForTable<List<LubricateEquipment>> findEquipLubricateByPage(LubricateEquipment equip, Integer page, Integer limit){
		log.info("------进入findEquipLubricateByPage执行分页查询换油设备");
		log.info("------查询条件为：{}", equip);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findEquipLubricationByPage(equip, page, limit);
	}
	
	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 设备lid
	 * @return
	 */
	@GetMapping("/record/query/lid")
	public ResultJsonForTable<List<LubricateEquipmentRecord>> findEquipLubricateRecordBylid(String lid, Integer page, Integer limit){
		log.info("------进入findEquipLubricateRecordBylid执行分页查询设备换油记录");
		log.info("------润滑设备lid={}", lid);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findEquipLubricationRecordByLid(lid, page, limit);
	}
	
	/**
	 * 临时换油记录跟踪
	 * @param lid 设备lid
	 * @return
	 */
	@GetMapping("/place/change/trace")
	public ResultJsonForTable<List<LubricateEquipmentPlace>> findEquipLubricationTrace(String positionnum, String tname, Integer page,
			Integer limit){
		log.info("------进入findEquipLubricationTrace执行临时换油记录跟踪");
		log.info("------换油设备位号={}", positionnum);
		log.info("------换油设备名称={}", tname);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findEquipLubricationTrace(positionnum, tname, page, limit);
	}
}
