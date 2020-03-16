
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2020年3月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller.spareparts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SparePart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sparepartsLedger")
@Api(value = "备件台账", tags = "备件台账")
public class SparepartsLedgerController {

	@RequestMapping("/getSparePartsInfo")
	@ApiOperation("获取备件数据列表")
	public Page<SparePart> getSparePartsInfo(QueryCondition condition) {
		
		//开启分页
		if(condition.getPage()!=null && condition.getLimit()!=null) {
			
		}
		
		return null;

	}

}
