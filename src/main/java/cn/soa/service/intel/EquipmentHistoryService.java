
/**
 * <一句话功能描述>
 * <p>设备历史操作/版本数据处理业务层接口
 * @author 陈宇林
 * @version [版本号, 2019年9月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel;

import com.github.pagehelper.Page;

import cn.soa.entity.EquipmentCommonInfoBack;
import cn.soa.entity.QueryCondition;

public interface EquipmentHistoryService {

	/**
	 * @param condition
	 * @return
	 */
	Page<EquipmentCommonInfoBack> getEquHistoryList(QueryCondition condition);
	
	
	/**
	 * 恢复设备数据
	 * @param condition
	 * @return
	 */
	String recoveryEquInfo(QueryCondition condition);

}
