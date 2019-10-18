
/**
 * <一句话功能描述>
 * <p>设备详情页面数据处理控制层
 * @author 陈宇林
 * @version [版本号, 2019年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.soa.entity.EquMaintenanceInfo;
import cn.soa.entity.EquipmentSpare;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.ResponseEntity;
import cn.soa.service.intel.EquipmentDetailService;

@RestController
@RequestMapping("/equipmentDetail")
public class EquipmentDetailController {

	@Autowired
	private EquipmentDetailService equipmentDetailService;

	/**
	 * 获取设备检维修信息
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getEquMaintenanceInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Page<EquMaintenanceInfo>> getEquMaintenanceInfo(@RequestBody QueryCondition condition) {
		ResponseEntity<Page<EquMaintenanceInfo>> resObj = new ResponseEntity<Page<EquMaintenanceInfo>>();

		// 开启分页
		PageHelper.startPage(condition.getPage(), condition.getLimit());
		
		try {
			Page<EquMaintenanceInfo> result = equipmentDetailService.getEquMaintenanceInfo(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setCount(result.getTotal());
			resObj.setMsg("query data success");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
		}
		return resObj;
	}
	
	/**
	 * 获取设备检维修信息
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getEquSpareInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<Page<EquipmentSpare>> getEquSpareInfo(@RequestBody QueryCondition condition) {
		ResponseEntity<Page<EquipmentSpare>> resObj = new ResponseEntity<Page<EquipmentSpare>>();

		// 开启分页
		PageHelper.startPage(condition.getPage(), condition.getLimit());
		
		try {
			Page<EquipmentSpare> result = equipmentDetailService.getEquSpareInfo(condition);
			resObj.setCode(0);
			resObj.setData(result);
			resObj.setCount(result.getTotal());
			resObj.setMsg("query data success");
		} catch (Exception e) {
			resObj.setCode(-1);
			resObj.setMsg("query data failed >>>" + e.getMessage());
			e.printStackTrace();
		}
		return resObj;
	}

}
