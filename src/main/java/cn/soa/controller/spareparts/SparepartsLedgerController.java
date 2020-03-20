
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2020年3月15日]
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.entity.spareparts.SparePart;
import cn.soa.service.intel.spareparts.SparepartsLedgerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sparepartsLedger")
@Api(value = "备件台账", tags = "备件台账")
@Slf4j
public class SparepartsLedgerController {

	@Autowired
	private SparepartsLedgerService sparepartsLedgerService;

	/**
	 * 获取设备备件数据列表
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getSparePartsInfo", method = RequestMethod.POST, produces = "application/json;chartset=utf-8")
	@ApiOperation("获取备件数据列表")
	public ResponseEntity<Page<SparePart>> getSparePartsInfo(
			@ApiParam("筛选条件，传sparePart即可；支持分页，需要开启分页时需要传page和limit参数；" + "当请求库存告警列表信息时，需传alarm参数，值为'true';"
					+ "通过备件分类过滤时，需传sparepartTypeId（备件分类id）参数") @RequestBody QueryCondition condition) {

		log.info("==============获取设备备件数据列表=============");

		// 开启分页
		if (condition.getPage() != null && condition.getLimit() != null) {
			log.info("==============开启分页=============");
			PageHelper.startPage(condition.getPage(), condition.getLimit());
		}
		System.out.println(condition.getAlarm());

		ResponseEntity<Page<SparePart>> resObj = new ResponseEntity<Page<SparePart>>();

		try {
			Page<SparePart> result = sparepartsLedgerService.getSparePartsInfo(condition);
			resObj.setCode(0);
			resObj.setCount(result.getTotal());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("==============获取设备备件数据列表成功=============");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("==============获取设备备件数据列表失败=============>>>" + e.getMessage());
		}

		return resObj;

	}

	/**
	 * 批量删除设备备件数据
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/delSparePartsInfo", method = RequestMethod.DELETE, produces = "application/json;chartset=utf-8")
	@ApiOperation("删除备件数据列表")
	public ResponseEntity<String> delSparePartsInfo(@ApiParam("设备备件信息对象数组") @RequestBody List<SparePart> spareParts) {

		log.info("==============删除设备备件数据列表=============");

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		try {
			String result = sparepartsLedgerService.delSparePartsInfo(spareParts);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("delete data success");
			log.info("==============删除设备备件数据列表成功=============");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("delete data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("==============删除设备备件数据列表失败=============>>>" + e.getMessage());
		}

		return resObj;

	}

	/**
	 * 
	 * 更新设备备件信息
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/updateSparePartsInfo", method = RequestMethod.PUT, produces = "application/json;chartset=utf-8")
	@ApiOperation("更新备件数据")
	public ResponseEntity<String> updateSparePartsInfo(
			@ApiParam("设备备件对象信息（根据需要传参数）") @RequestBody SparePart sparePart) {

		log.info("==============更新设备备件数据=============");

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		try {
			String result = sparepartsLedgerService.updateSparePartsInfo(sparePart);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("update data success");
			log.info("==============更新设备备件数据成功=============");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("update data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("==============更新设备备件数据失败=============>>>" + e.getMessage());
		}

		return resObj;

	}

	/**
	 * 
	 * 添加设备备件信息
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/addSparePartsInfo", method = RequestMethod.POST, produces = "application/json;chartset=utf-8")
	@ApiOperation("添加备件数据")
	public ResponseEntity<String> addSparePartsInfo(@ApiParam("设备备件信息对象数组") @RequestBody List<SparePart> spareParts) {

		log.info("==============添加设备备件数据=============");

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		try {
			String result = sparepartsLedgerService.addSparePartsInfo(spareParts);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("==============添加设备备件数据成功=============");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("==============添加设备备件数据失败=============>>>" + e.getMessage());
		}

		return resObj;

	}

	/**
	 * 通过设备id获取设备备件数据列表
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getSparePartsInfoByEquId", method = RequestMethod.POST, produces = "application/json;chartset=utf-8")
	@ApiOperation("通过设备id获取设备备件数据列表")
	public ResponseEntity<Page<SparePart>> getSparePartsInfoByEquId(
			@ApiParam("支持分页，需要开启分页时需要传page和limit参数,equId为必传参数") @RequestBody QueryCondition condition) {

		ResponseEntity<Page<SparePart>> resObj = new ResponseEntity<Page<SparePart>>();

		log.info("==============通过设备id获取设备备件数据列表=============");

		// 开启分页
		if (condition.getPage() != null && condition.getLimit() != null) {
			log.info("==============开启分页=============");
			PageHelper.startPage(condition.getPage(), condition.getLimit());
		}

		try {
			Page<SparePart> result = sparepartsLedgerService.getSparePartsInfoByEquId(condition);
			resObj.setCode(0);
			resObj.setCount(result.getTotal());
			resObj.setData(result);
			resObj.setMsg("query data success");
			log.info("==============通过设备id获取设备备件数据列表成功=============");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("==============通过设备id获取设备备件数据列表失败=============>>>" + e.getMessage());
		}

		return resObj;

	}

}
