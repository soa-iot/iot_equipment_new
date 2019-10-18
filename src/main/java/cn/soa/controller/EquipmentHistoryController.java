
/**
 * <一句话功能描述>
 * <p>设备历史操作/版本数据处理控制层
 * @author 陈宇林
 * @version [版本号, 2019年9月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.soa.entity.EquipmentCommonInfoBack;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.service.intel.EquipmentHistoryService;

@RestController
@RequestMapping("/equipmentHistory")
public class EquipmentHistoryController {

	@Autowired
	private EquipmentHistoryService equipmentHistoryService;

	/**
	 * 获取设备历史操作数据
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getEquHistoryList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Page<EquipmentCommonInfoBack>> getEquHistoryList(@RequestBody QueryCondition condition) {

		System.out.println(condition);
		PageHelper.startPage(condition.getPage(), condition.getLimit());

		ResponseEntity<Page<EquipmentCommonInfoBack>> resObj = new ResponseEntity<Page<EquipmentCommonInfoBack>>();

		try {

			Page<EquipmentCommonInfoBack> result = equipmentHistoryService.getEquHistoryList(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setCount(result.getTotal());
			resObj.setMsg("query data succedd");

		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
		}

		return resObj;
	}

	/**
	 * 恢复设备数据
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/recoveryEquInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> recoveryEquInfo(@RequestBody QueryCondition condition) {
		ResponseEntity<String> resObj = new ResponseEntity<String>();
		try {

			String result = equipmentHistoryService.recoveryEquInfo(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setMsg("recovery data success");

		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("recovery data failed >>>" + e.getMessage());
		}

		return resObj;
	}

}
