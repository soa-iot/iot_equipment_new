package cn.soa.controller.lubrication;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;
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
	
	@Autowired
	private EquipmentLubricationOilSI equipmentLubricationOilSI;
	
	/**
	 * 新增油品
	 * @return
	 */
	@RequestMapping("/addoil")
	public ResultJson<Integer> addOil(EquipmentLubricationOil equipmentLubricationOil,String userid,String rnote) {
		
		log.info("===============================新增油品："+equipmentLubricationOil.toString());
		Integer row = equipmentLubricationOilSI.addOil(equipmentLubricationOil,userid,rnote);
		log.info("===============================新增油品数量："+row);
		if (row > 0 ) {
			return new ResultJson<Integer>(0, "新增油品成功");
		}else {
			return new ResultJson<Integer>(1, "新增油品失败");
		}
	}
	
	/**
	 * 根据油品库存条件查询油品
	 * @return
	 */
	@RequestMapping("/findoilbyconditions")
	public ResultJson<List<EquipmentLubricationOil>> findOilbyConditions(EquipmentLubricationOil equipmentLubricationOil) {
		
		log.info("===============================查询油品："+equipmentLubricationOil.toString());
		List<EquipmentLubricationOil> equipmentLubricationOils = equipmentLubricationOilSI.findOilbyConditions(equipmentLubricationOil);
		log.info("===============================查询油品："+equipmentLubricationOils);
		if (equipmentLubricationOils != null ) {
			return new ResultJson<List<EquipmentLubricationOil>>(0, "查询油品成功",equipmentLubricationOils);
		}else {
			return new ResultJson<List<EquipmentLubricationOil>>(1, "查询油品失败",null);
		}
	}
	
	/**
	 * 出入库记录查询
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/queryoilall")
	public ResultJsonForTable<List<EquipmentOilRecordVO>> queryOilAll(Integer page,Integer limit,String oid, String startTime,String endTime) {
		
		log.info("==============================出入库记录查询================================");
		
		log.info("==================oid："+oid);
		log.info("==================startTime："+startTime);
		
		log.info("==================endTime："+endTime);
		
		log.info("===================每页条数："+limit+"第几页："+page);
		 List<EquipmentOilRecordVO> equipmentOilRecordVOs = equipmentLubricationOilSI.queryOilAll(page, limit, oid.trim(), startTime, endTime);
		log.info("===============================油品出入库记录数量："+equipmentOilRecordVOs);
		if (equipmentOilRecordVOs != null ) {
			return new ResultJsonForTable<List<EquipmentOilRecordVO>>(0, "出入库记录查询成功", equipmentLubricationOilSI.countRecord(oid.trim(), startTime, endTime), equipmentOilRecordVOs);
		}else {
			return new ResultJsonForTable<List<EquipmentOilRecordVO>>(1, "出入库记录查询失败", 0, null);
		}
	}
	
	/**
	 * 所有油品查询
	 * @return
	 */
	@RequestMapping("/queryoilallstock")
	public ResultJsonForTable<List<EquipmentLubricationOil>> queryOilAllStock(Integer page,Integer limit) {
		
		log.info("==============================所有油品查询================================");
		log.info("===================每页条数："+limit+"第几页："+page);
		 List<EquipmentLubricationOil> equipmentLubricationOils = equipmentLubricationOilSI.queryOilAllStock(page,limit);
		log.info("===============================所有油品："+equipmentLubricationOils);
		if (equipmentLubricationOils != null ) {
			return new ResultJsonForTable<List<EquipmentLubricationOil>>(0, "出入库记录查询成功", equipmentLubricationOilSI.countStock(), equipmentLubricationOils);
		}else {
			return new ResultJsonForTable<List<EquipmentLubricationOil>>(1, "出入库记录查询失败", 0, null);
		}
	}
		/**
		 * 油品入库
		 * @return
		 */
		@RequestMapping("/oilstock")
		public ResultJson<Integer> oilStock(String oname,String ramount,String rnote,String userid) {
			
			log.info("==============================所有油品查询================================");
			log.info("===================油品名称："+oname+"；入库数量："+ramount+";备注rnote："+rnote+";操作人userid："+userid);
			 Integer row = equipmentLubricationOilSI.oilStock(oname,ramount,rnote,userid);
			log.info("===============================入库油品更新条数："+row);
			if (row > 0 ) {
				return new ResultJson<Integer>(0, "油品入库成功", row);
			}else {
				return new ResultJson<Integer>(1, "油品入库失败", row);
			}
	}
}
