
/**
 * <一句话功能描述>
 * <p>备件出入库服务层接口
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel.spareparts;

import com.github.pagehelper.Page;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SpPutIn;

public interface SparepartOutInService {

	/**
	 * 根据条件获取备件申请单
	 * @param condition
	 * @return
	 */
	Page<SpPutIn> getSparepartApply(QueryCondition condition);

}
