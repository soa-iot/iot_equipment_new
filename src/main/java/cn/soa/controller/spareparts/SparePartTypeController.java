
/**
 * <一句话功能描述>
 * <p>设备备件分类相关操作控制层
 * @author 陈宇林
 * @version [版本号, 2020年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller.spareparts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResponseEntity;
import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.ClassifySpRelation;
import cn.soa.entity.spareparts.SpClassify;
import cn.soa.service.intel.spareparts.SparePartTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(value = "设备备件分类", tags = "设备备件分类")
@RequestMapping("/sparePartType")
public class SparePartTypeController {

	@Autowired
	private SparePartTypeService sparePartTypeService;

	/**
	 * 获取设备备件分类树（树形结构）
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取设备备件分类树（树形结构）")
	@RequestMapping(value = "/getSparepartsClassInfoAsTree", method = RequestMethod.GET)
	public ResponseEntity<List<TreeObject>> getSparepartsClassInfoAsTree() {

		log.info("=================获取设备备件分类树（树形结构）====================");

		ResponseEntity<List<TreeObject>> resObj = new ResponseEntity<List<TreeObject>>();
		try {
			List<TreeObject> result = sparePartTypeService.getSparepartsClassInfoAsTree();
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("=================获取设备备件分类树（树形结构）成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================获取设备备件分类树（树形结构）失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}

	/**
	 * 添加设备分类数据
	 * @param SpClassifies
	 * @return
	 */
	@ApiOperation(value = "添加设备备件分类数据")
	@RequestMapping(value = "/addSparepartsClassInfo", method = RequestMethod.POST, produces = "application/json;chartset-utf-8")
	public ResponseEntity<String> addSparepartsClassInfo(@ApiParam("设备分类信息对象数组") @RequestBody List<SpClassify> SpClassifies) {

		log.info("=================添加设备备件分类数据====================");

		ResponseEntity<String> resObj = new ResponseEntity<String>();
		
		try {
			String result = sparePartTypeService.addSparepartsClassInfo(SpClassifies);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("add data success");
			log.info("=================添加设备备件分类数据成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("add data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================添加设备备件分类数据失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}
	
	/**
	 * 删除设备分类数据
	 * @param SpClassifies
	 * @return
	 */
	@ApiOperation(value = "删除设备备件分类数据")
	@RequestMapping(value = "/delSparepartsClassInfo", method = RequestMethod.DELETE, produces = "application/json;chartset-utf-8")
	public ResponseEntity<String> delSparepartsClassInfo(@ApiParam("设备分类信息对象数组") @RequestBody List<SpClassify> spClassifies) {

		log.info("=================删除设备备件分类数据====================");

		ResponseEntity<String> resObj = new ResponseEntity<String>();
		
		try {
			String result = sparePartTypeService.delSparepartsClassInfo(spClassifies);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("delete data success");
			log.info("=================删除设备备件分类数据成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("delete data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================删除设备备件分类数据失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}
	
	/**
	 * 更新设备分类数据
	 * @param SpClassifies
	 * @return
	 */
	@ApiOperation(value = "更新设备备件分类数据")
	@RequestMapping(value = "/updateSparepartsClassInfo", method = RequestMethod.PUT, produces = "application/json;chartset-utf-8")
	public ResponseEntity<String> updateSparepartsClassInfo(@ApiParam("设备分类信息对象数组") @RequestBody SpClassify spClassify) {

		log.info("=================更新设备备件分类数据====================");

		ResponseEntity<String> resObj = new ResponseEntity<String>();
		
		try {
			String result = sparePartTypeService.updateSparepartsClassInfo(spClassify);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("update data success");
			log.info("=================更新设备备件分类数据成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("update data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================更新设备备件分类数据失败！！！！====================" + e.getMessage());
		}
		return resObj;
	}
	
	/**
	 * 备件分类与备件关系数据添加
	 * 
	 * @return
	 */
	@ApiOperation(value = "备件分类与备件关系数据添加")
	@RequestMapping(value = "/addClassifySpRelation", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> addClassifySpRelation(
			@ApiParam("备件分类与备件关系对象") @RequestBody List<ClassifySpRelation> classifySpRelations) {


		log.info("=================备件分类与备件关系数据添加====================");
		ResponseEntity<String> resObj = new ResponseEntity<String>();
		
		try {
			String result = sparePartTypeService.addClassifySpRelation(classifySpRelations);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("add data success");
			log.info("=================备件分类与备件关系数据添加成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("add data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================备件分类与备件关系数据添加失败！！！！====================" + e.getMessage());
		}
		
		return resObj;
	}
	
	/**
	 * 备件分类与备件关系数据删除
	 * 
	 * @return
	 */
	@ApiOperation(value = "备件分类与备件关系数据删除")
	@RequestMapping(value = "/delClassifySpRelation", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> delClassifySpRelation(
			@ApiParam("备件分类与备件关系对象") @RequestBody List<ClassifySpRelation> classifySpRelations) {


		log.info("=================备件分类与备件关系数据删除====================");
		ResponseEntity<String> resObj = new ResponseEntity<String>();
		
		try {
			String result = sparePartTypeService.delClassifySpRelation(classifySpRelations);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("delete data success");
			log.info("=================备件分类与备件关系数据删除成功====================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("delete data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.debug("=================备件分类与备件关系数据删除失败！！！！====================" + e.getMessage());
		}
		
		return resObj;
	}

}
