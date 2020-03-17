
/**
 * <一句话功能描述>
 * <p>备件出入库相关操作控制层
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller.spareparts;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.entity.spareparts.SpPutIn;
import cn.soa.service.intel.spareparts.SparepartOutInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "备件出入库管理", tags = "备件出入库管理")
@RequestMapping("sparepartOutIn")
@Slf4j
public class SparepartOutInController {

	@Autowired
	private SparepartOutInService sparepartOutInService;

	/**
	 * 获取采购申请单列表数据
	 * 
	 * @param spPutIn
	 * @return
	 */
	@RequestMapping(value = "/getSparepartApply", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation("获取采购申请单列表数据")
	public ResponseEntity<Page<SpPutIn>> getSparepartApply(@ApiParam("查询条件，支持分页，需要开启分页时需要传page和limit参数；"
			+ "过滤需要传spPutIn对象，领用和申请用spPutIn的type属性过滤，若要匹配申请日期，需根据需要传beginDate、endDate两个参数") @RequestBody QueryCondition condition) {

		log.info("====================获取采购申请单列表数据========================");

		// 开启分页
		if (condition.getPage() != null && condition.getLimit() != null) {
			PageHelper.startPage(condition.getPage(), condition.getLimit());
		}

		ResponseEntity<Page<SpPutIn>> resObj = new ResponseEntity<Page<SpPutIn>>();

		try {
			Page<SpPutIn> result = sparepartOutInService.getSparepartApply(condition);
			resObj.setCode(0);
			resObj.setCount(result.size());
			resObj.setData(result);
			resObj.setMsg("query data sucess");
			log.info("====================获取采购申请单列表数据成功========================");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
			log.info("====================获取采购申请单列表数据失败========================>>>" + e.getMessage());
		}

		return resObj;
	}

}
