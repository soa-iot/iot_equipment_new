
/**
 * <一句话功能描述>
 * <p>备件采购/领用申请控制层
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller.spareparts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.ResponseEntity;
import cn.soa.entity.spareparts.SparepartApply;
import cn.soa.service.intel.spareparts.SparepartApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sparepartApply")
@Api(value = "备件申请（采购/领用）", tags = "备件申请（采购/领用）")
@Slf4j
public class SparepartApplyController {

	@Autowired
	private SparepartApplyService sparepartApplyService;

	/**
	 * 备件采购/领用申请
	 * 
	 * @param sparepartApply
	 * @return
	 */
	@ApiOperation(value = "添加备件采购/领用申请")
	@RequestMapping(value = "/addSparepartApply", method = RequestMethod.POST, produces = "application/json;")
	public ResponseEntity<String> addSparepartApply(
			@ApiParam("包含申请单、出入库记录数组") @RequestBody SparepartApply sparepartApply) {

		log.info("======================添加备件采购/领用申请=========================");

		ResponseEntity<String> resObj = new ResponseEntity<String>();

		try {
			String result = sparepartApplyService.addSparepartApply(sparepartApply);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("add data success");
			log.info("======================添加采购/领用申请成功=========================");

		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("add data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("======================添加采购/领用申请失败=========================>>>" + e.getMessage());
		}

		return resObj;
	}

}
