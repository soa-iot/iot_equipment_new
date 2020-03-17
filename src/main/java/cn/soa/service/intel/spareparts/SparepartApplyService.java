
/**
 * <一句话功能描述>
 * <p>备件采购/领用申请业务层接口
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel.spareparts;

import cn.soa.entity.spareparts.SparepartApply;

public interface SparepartApplyService {

	/**
	 * 
	 * 备件采购/领用申请
	 * @param sparepartApply
	 * @return
	 */
	String addSparepartApply(SparepartApply sparepartApply);

}
