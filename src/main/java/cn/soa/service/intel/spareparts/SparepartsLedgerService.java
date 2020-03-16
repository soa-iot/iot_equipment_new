
/**
 * <一句话功能描述>
 * <p>备件台账服务层接口
 * @author 陈宇林
 * @version [版本号, 2020年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel.spareparts;

import java.util.List;

import com.github.pagehelper.Page;

import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SparePart;

public interface SparepartsLedgerService {

	/**
	 * 获取设备备件数据列表
	 * @param condition
	 * @return
	 */
	Page<SparePart> getSparePartsInfo(QueryCondition condition);

	/**
	 * 
	 * 删除设备备件
	 * @param spareParts
	 * @return
	 */
	String delSparePartsInfo(List<SparePart> spareParts);

	/**
	 * 
	 * 更新设备备件信息
	 * @param sparePart
	 * @return
	 */
	String updateSparePartsInfo(SparePart sparePart);

	/**
	 * 
	 * 批量添加设备备件信息数据
	 * @param spareParts
	 * @return
	 */
	String addSparePartsInfo(List<SparePart> spareParts);

}
