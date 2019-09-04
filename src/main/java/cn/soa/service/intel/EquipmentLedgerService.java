
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2019年8月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.Page;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.EquipmentType;
import cn.soa.entity.QueryCondition;

public interface EquipmentLedgerService {

	/**
	 * 获取所有设备分类信息
	 * 
	 * @return
	 */
	List<EquipmentType> getAllEquipmentType();

	/**
	 * 根据条件获取设备表头
	 * 
	 * @param condition
	 * @return
	 */
	List<EquipmentDisplayInfo> getEquipmentDisplayInfo(QueryCondition condition);

	/**
	 * 根据条件获取设备数据
	 * 
	 * @param condition
	 * @return
	 */
	Page<EquipmentCommonInfo> getEquipmentList(QueryCondition condition);

	/**
	 * 根据条件获取表单扩展属性名称
	 * 
	 * @return
	 */
	List<EquipmentDisplayInfo> getFormInfo(QueryCondition condition);

	/**
	 * 新增设备记录
	 * 
	 * @param equipmentCommonInfo
	 * @return
	 */
	String addEquipmentRecord(EquipmentCommonInfo equipmentCommonInfo);

	/**
	 * 
	 * 更新设备数据
	 * 
	 * @param equipmentCommonInfo
	 * @return
	 */
	String updateEquipmentRecord(EquipmentCommonInfo equipmentCommonInfo);

	/**
	 * 删除设备数据
	 * 
	 * @param equipmentCommonInfo
	 * @return
	 */
	String delEquipmentRecord(List<EquipmentCommonInfo> equipmentCommonInfos);

	/**
	 * 
	 * 导出Excel
	 * 
	 * @param condition
	 * @param response
	 * @throws Exception 
	 */
	void exportEquipment(QueryCondition condition, HttpServletResponse response) throws Exception;

}
