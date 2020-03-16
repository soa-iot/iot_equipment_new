
/**
 * <一句话功能描述>
 * <p>备件管理控制层
 * @author 陈宇林
 * @version [版本号, 2020年3月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller.spareparts;

import java.util.List;

import javax.management.Query;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import ch.qos.logback.core.joran.conditional.Condition;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.EqOrSpRelation;
import cn.soa.service.intel.spareparts.SparePartManangerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sparepatsManager")
@Api(value = "设备与备件关系维护", tags = "设备与备件关系维护")
public class SparePartManangerController {

	// 备件管理服务层
	@Autowired
	private SparePartManangerService sparePartManangerService;

	/**
	 * 获取设备分类树（树形结构）
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取设备分类树（树形结构）")
	@RequestMapping(value = "/getSparepartsClassInfoAsTree", method = RequestMethod.GET)
	public ResponseEntity<List<TreeObject>> getSparepartsClassInfoAsTree() {

		log.info("=================获取设备分类树（树形结构）====================");

		ResponseEntity<List<TreeObject>> resObj = new ResponseEntity<List<TreeObject>>();
		try {
			List<TreeObject> result = sparePartManangerService.getSparepartsClassInfoAsTree();
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("=================获取设备分类树（树形结构）成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================获取设备分类树（树形结构）失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}

	/**
	 * 获取设备基本信息列表数据
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取设备基本信息")
	@RequestMapping(value = "/getEquInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Page<EquipmentCommonInfo>> getEquInfo(
			@ApiParam("筛选条件,传equipmentCommonInfo即可") @RequestBody QueryCondition condition) {

		// 开启分页
		if (condition.getPage() != null && condition.getLimit() != null) {
			PageHelper.startPage(condition.getPage(), condition.getLimit());
		}

		log.info("=================获取设备基本信息====================");

		ResponseEntity<Page<EquipmentCommonInfo>> resObj = new ResponseEntity<Page<EquipmentCommonInfo>>();
		try {
			// 查询设备数据
			Page<EquipmentCommonInfo> result = sparePartManangerService.getEquInfo(condition);
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("=================获取设备基本信息成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================获取设备基本信息失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}

	/**
	 * 获取设备基本信息表字段下拉框数据
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取设备基本信息表字段下拉框数据")
	@RequestMapping(value = "/getEquBaseColumn", method = RequestMethod.GET)
	public ResponseEntity<List<EquipmentDisplayInfo>> getEquBaseColumn(
			@ApiParam("设备分类ID") @RequestParam("equTypeId") String equTypeId) {

		log.info("=================获取设备基本信息表字段下拉框数据====================");

		ResponseEntity<List<EquipmentDisplayInfo>> resObj = new ResponseEntity<List<EquipmentDisplayInfo>>();
		try {
			List<EquipmentDisplayInfo> result = sparePartManangerService.getEquBaseColumn(equTypeId);
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("=================获取设备基本信息表字段下拉框数据成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================获取设备基本信息表字段下拉框数据失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}
	
	
	/**
	 * 获取设备与备件关系数据
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取设备与备件关系数据")
	@RequestMapping(value = "/getEquSpareRe", method = RequestMethod.GET)
	public ResponseEntity<List<EqOrSpRelation>> getEquSpareRe(
			@ApiParam("设备表id")  @RequestParam("eqId") String eqId) {

		log.info("=================获取设备与备件关系数据====================");

		ResponseEntity<List<EqOrSpRelation>> resObj = new ResponseEntity<List<EqOrSpRelation>>();
		try {
			List<EqOrSpRelation> result = sparePartManangerService.getEquSpareRe(eqId);
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("=================获取设备与备件关系数据成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================获取设备与备件关系数据失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}
	
	
	/**
	 * 设备与备件关系数据添加
	 * 
	 * @return
	 */
	@ApiOperation(value = "设备与备件关系数据添加")
	@RequestMapping(value = "/addEquSpareRe", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> addEquSpareRe(
			@ApiParam("设备与备件关系对象数组") @RequestBody List<EqOrSpRelation> eqOrSpRelations) {


		log.info("=================设备与备件关系数据添加====================");

		ResponseEntity<String> resObj = new ResponseEntity<String>();
		try {
			// 查询设备数据
			String result = sparePartManangerService.addEquSpareRe(eqOrSpRelations);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("add data success");
			log.info("=================设备与备件关系数据添加成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("add data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================设备与备件关系数据添加失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}
	
	/**
	 * 设备与备件关系数据删除
	 * 
	 * @return
	 */
	@ApiOperation(value = "设备与备件关系数据删除")
	@RequestMapping(value = "/delEquSpareRe", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> delEquSpareRe(
			@ApiParam("设备与备件关系对象数组，（对象属性可只传ID）") @RequestBody List<EqOrSpRelation> eqOrSpRelations) {


		log.info("=================设备与备件关系数据删除====================");
		ResponseEntity<String> resObj = new ResponseEntity<String>();
		
		try {
			// 查询设备数据
			String result = sparePartManangerService.delEquSpareRe(eqOrSpRelations);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("add data success");
			log.info("=================设备与备件关系数据删除成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("add data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================设备与备件关系数据删除失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}

}
