
/**
 * <一句话功能描述>
 * <p> 设备备件相关excel导入导出控制层
 * @author 陈宇林
 * @version [版本号, 2020年3月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller.spareparts;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.service.intel.spareparts.SparepartsExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("SparepartsExcel")
@Api(value = "设备备件excel导入导出管理", tags = "设备备件excel导入导出管理")
@Slf4j
public class SparepartsExcelController {

	@Autowired
	private SparepartsExcelService sparepartsExcelService;

	/**
	 * 设备与备件关系导入
	 * 
	 * @param exportFile
	 * @return
	 */
	@PostMapping("/importEqOrSpRe")
	@ApiOperation("设备与备件关系导入")
	public ResponseEntity<String> importEqOrSpRe(
			@ApiParam("excel文件对象") @RequestParam("exportFile") MultipartFile exportFile) {

		log.info("=====================设备与备件关系导入=======================");

		ResponseEntity<String> resObj = new ResponseEntity<>();
		try {
			String msg = sparepartsExcelService.importEqOrSpRe(exportFile);
			if (msg.contains("error")) {
				resObj.setMsg(msg);
				resObj.setCode(-1);
				resObj.setData(msg);
				log.info("=====================设备与备件关系导入失败=======================>>>" + msg);
			} else {
				resObj.setMsg(msg);
				resObj.setCode(0);
				resObj.setData(msg);
				log.info("=====================设备与备件关系导入成功=======================");
			}
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("import data failed >>>>" + e.getMessage());
			resObj.setData("设备与备件关系导入失败，请联系管理员！！！");
			e.printStackTrace();
			log.info("=====================设备与备件关系导入失败=======================>>>" + e.getMessage());
		}

		return resObj;
	}

	/**
	 * 备件导入
	 * 
	 * @param exportFile
	 * @return
	 */
	@PostMapping("/importSparepart")
	@ApiOperation("备件导入")
	public ResponseEntity<String> importSparepart(
			@ApiParam("excel文件对象") @RequestParam("exportFile") MultipartFile exportFile) {

		log.info("=====================备件导入=======================");

		ResponseEntity<String> resObj = new ResponseEntity<>();
		try {
			String msg = sparepartsExcelService.importSparepart(exportFile);
			if (msg.contains("error")) {
				resObj.setMsg(msg);
				resObj.setCode(-1);
				resObj.setData(msg);
				log.info("=====================设备件导入失败=======================>>>" + msg);
			} else {
				resObj.setMsg(msg);
				resObj.setCode(0);
				resObj.setData(msg);
				log.info("=====================备件导入成功=======================");
			}
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("import data failed >>>>" + e.getMessage());
			resObj.setData("备件导入失败，请联系管理员！！！");
			e.printStackTrace();
			log.info("=====================备件导入失败=======================>>>" + e.getMessage());
		}

		return resObj;
	}

	/**
	 * 备件导出（xls）
	 * 
	 * @param exportFile
	 * @return
	 */
	@PostMapping("/exportSparepart")
	@ApiOperation("备件导出（xls）")
	public void exportSparepart(
			@ApiParam("筛选条件") @RequestBody QueryCondition condition,
			HttpServletResponse response) {

		log.info("=====================备件导出（xls）=======================");

		try {
			sparepartsExcelService.exportEquipment(condition, response);
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

}
