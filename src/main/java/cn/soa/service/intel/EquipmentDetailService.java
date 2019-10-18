
/**
 * <一句话功能描述>
 * <p>设备详情页面数据处理业务层接口
 * @author 陈宇林
 * @version [版本号, 2019年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel;

import java.util.List;

import com.github.pagehelper.Page;

import cn.soa.entity.EquMaintenanceInfo;
import cn.soa.entity.EquipmentSpare;
import cn.soa.entity.QueryCondition;

public interface EquipmentDetailService {

	/**
	 * @param condition
	 * @return
	 */
	Page<EquMaintenanceInfo> getEquMaintenanceInfo(QueryCondition condition);

	/**
	 * @param condition
	 * @return
	 */
	Page<EquipmentSpare> getEquSpareInfo(QueryCondition condition);

}
