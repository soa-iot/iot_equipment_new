
/**
 * <一句话功能描述>
 * <p> 设备备件管理服务层接口
 * @author 陈宇林
 * @version [版本号, 2020年3月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel.spareparts;

import java.util.List;

import com.github.pagehelper.Page;

import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.ClassifySpRelation;
import cn.soa.entity.spareparts.EqOrSpRelation;

public interface SparePartManangerService {


	/**
	 * 
	 * 根据条件查询设备基本数据
	 * @param condition
	 * @return
	 */
	Page<EquipmentCommonInfo> getEquInfo(QueryCondition condition);

	/**
	 * 获取获取设备基本信息表字段下拉框数据
	 * @param equTypeId 
	 * @return
	 */
	List<EquipmentDisplayInfo> getEquBaseColumn(String equTypeId);

	/**
	 * 
	 * 批量添加设备与备件关系数据
	 * @param eqOrSpRelations
	 * @return
	 */
	String addEquSpareRe(List<EqOrSpRelation> eqOrSpRelations);

	/**
	 * 
	 * 
	 * 删除设备
	 * @param eqOrSpRelations
	 * @return
	 */
	String delEquSpareRe(List<EqOrSpRelation> eqOrSpRelations);

	/**
	 * 
	 * 根据设备ID查询设备对应的备件关联关系
	 * @param eqId
	 * @return
	 */
	List<EqOrSpRelation> getEquSpareRe(String eqId);




}
