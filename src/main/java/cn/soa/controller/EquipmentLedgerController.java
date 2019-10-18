
/**
 * <一句话功能描述>
 * <p>设备台账页面数据处理接口控制层
 * @author 陈宇林
 * @version [版本号, 2019年8月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller;

import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.EquipmentType;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.service.intel.EquipmentLedgerService;

@RestController
@RequestMapping("/equipmentLedger")
public class EquipmentLedgerController {

	@Autowired
	private EquipmentLedgerService equipmentLedgerService;

	/**
	 * 获取设备分类分析
	 * 
	 * @return
	 */
	@RequestMapping("/getAllEquipmentType")
	public ResponseEntity<List<EquipmentType>> getAllEquipmentType() {

		ResponseEntity<List<EquipmentType>> resObj = new ResponseEntity<List<EquipmentType>>();

		try {

			List<EquipmentType> result = equipmentLedgerService.getAllEquipmentType();

			resObj.setCode(0);
			resObj.setData(result);
			resObj.setCode(result.size());
			resObj.setMsg("query data success");

		} catch (Exception e) {
			e.printStackTrace();
			resObj.setCode(0);
			resObj.setMsg("query data field >>>" + e.getMessage());
		}

		return resObj;
	}

	/**
	 * 根据设备类型获取设备表头参数信息
	 * 
	 * @param equTypeId
	 *            设备类型id
	 * @return
	 */
	@RequestMapping("/getEquipmentDisplayInfo")
	public ResponseEntity<List<EquipmentDisplayInfo>> getEquipmentDisplayInfo(
			@RequestParam("equTypeId") String equTypeId) {

		QueryCondition condition = new QueryCondition();
		condition.setEquTypeId(equTypeId);
		ResponseEntity<List<EquipmentDisplayInfo>> resObj = new ResponseEntity<List<EquipmentDisplayInfo>>();
		try {
			List<EquipmentDisplayInfo> result = equipmentLedgerService.getEquipmentDisplayInfo(condition);
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data success");
		} catch (Exception e) {
			resObj.setCode(0);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;
	}

	/**
	 * 根据条件查询设备数据
	 * 
	 * @param equTypeId
	 * @param equId
	 * @return
	 */
	@RequestMapping(value = "/getEquipmentList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Page<EquipmentCommonInfo>> getEquipmentList(@RequestBody QueryCondition condition) {

		PageHelper.startPage(condition.getPage(), condition.getLimit());

		// System.out.println(condition);
		ResponseEntity<Page<EquipmentCommonInfo>> resObj = new ResponseEntity<Page<EquipmentCommonInfo>>();

		try {

			Page<EquipmentCommonInfo> result = equipmentLedgerService.getEquipmentList(condition);
			resObj.setCode(0);
			resObj.setMsg("query data success");
			resObj.setCount(result.getTotal());
			resObj.setData(result);
		} catch (Exception e) {
			resObj.setCode(0);
			resObj.setMsg("query data field >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;
	}

	/**
	 * 根据条件获取表单扩展属性名称
	 * 
	 * @param equTypeId
	 * @return
	 */
	@RequestMapping("/getFormInfo")
	public ResponseEntity<List<EquipmentDisplayInfo>> getFormInfo(@RequestParam("equTypeId") String equTypeId) {

		ResponseEntity<List<EquipmentDisplayInfo>> resObj = new ResponseEntity<List<EquipmentDisplayInfo>>();

		QueryCondition condition = new QueryCondition();
		condition.setEquTypeId(equTypeId);

		try {

			List<EquipmentDisplayInfo> result = equipmentLedgerService.getFormInfo(condition);

			resObj.setCode(0);
			resObj.setMsg("query data success");
			resObj.setData(result);
			resObj.setCount(result.size());

		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;

	}

	/**
	 * 新增设备数据
	 * 
	 * @param equipmentCommonInfo
	 * @return
	 */
	@RequestMapping(value = "/addEquipmentRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> addEquipmentRecord(@RequestBody EquipmentCommonInfo equipmentCommonInfo) {

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		// System.out.println(equipmentCommonInfo);
		try {

			String result = equipmentLedgerService.addEquipmentRecord(equipmentCommonInfo);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("add record success");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("add record failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;

	}

	/**
	 * 更新设备数据
	 * 
	 * @param equipmentCommonInfo
	 * @return
	 */
	@RequestMapping(value = "/updateEquipmentRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> updateEquipmentRecord(@RequestBody QueryCondition condition) {

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		System.out.println(condition);
		try {

			String result = equipmentLedgerService.updateEquipmentRecord(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("update record success");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("update record failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;

	}

	/**
	 * 删除设备数据
	 * 
	 * @param equipmentCommonInfo
	 * @return
	 */
	@RequestMapping(value = "/delEquipmentRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> delEquipmentRecord(@RequestBody QueryCondition condition) {

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		try {

			String result = equipmentLedgerService.delEquipmentRecord(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("del record success");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("del record failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;

	}

	/**
	 * 导出设备台账（xls）
	 * 
	 * @param exportType
	 * @param equTypeId
	 * @param equIds
	 * @param equType
	 * @param response
	 */
	@RequestMapping(value = "/exportEquipment", method = RequestMethod.POST)
	public void exportEquipment(String exportType, String equTypeId, String equIds, String equType,
			HttpServletResponse response) {
		QueryCondition condition = new QueryCondition();
		condition.setEquTypeId(equTypeId);
		condition.setExportType(exportType);
		condition.setEquType(equType);
		System.out.println(condition);
		try {
			equipmentLedgerService.exportEquipment(condition, response);
		} catch (Exception e) {
			try {
				OutputStream os = new BufferedOutputStream(response.getOutputStream());
				os.write("导出Excel出错，请联系管理员！！！".getBytes());
				os.flush();
				os.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 导入excel数据
	 * 
	 * @param exportFile
	 * @param equTypeId
	 * @return
	 */
	@PostMapping("/importEquipment")
	public ResponseEntity<String> importEquipment(@RequestParam("exportFile") MultipartFile exportFile,
			String equTypeId) {

		ResponseEntity<String> resObj = new ResponseEntity<>();

		System.out.println(exportFile);
		System.out.println(equTypeId);
		QueryCondition condition = new QueryCondition();
		condition.setEquTypeId(equTypeId);

		try {
			String msg = equipmentLedgerService.importEquipment(exportFile, equTypeId);
			if (msg.contains("error")) {
				resObj.setMsg(msg);
				resObj.setCode(-1);
				resObj.setData(msg);
			} else {
				resObj.setMsg(msg);
				resObj.setCode(0);
				resObj.setData(msg);
			}
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("import equipment record failed >>>>" + e.getMessage());
			resObj.setData("导入设备记录失败，请联系管理员！！！");
			e.printStackTrace();
		}

		return resObj;
	}

	/**
	 * 根据条件获取可搜索表单属性
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getSearchFormInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<List<EquipmentDisplayInfo>> getSearchFormInfo(QueryCondition condition) {

		ResponseEntity<List<EquipmentDisplayInfo>> resObj = new ResponseEntity<List<EquipmentDisplayInfo>>();

		try {
			List<EquipmentDisplayInfo> result = equipmentLedgerService.getSearchFormInfo(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setCount(result.size());
			resObj.setMsg("query data success");

		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;
	}

}
