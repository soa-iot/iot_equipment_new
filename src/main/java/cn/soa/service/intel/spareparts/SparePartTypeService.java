
/**
 * <一句话功能描述>
 * <p>设备备件分类服务层
 * @author 陈宇林
 * @version [版本号, 2020年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel.spareparts;

import java.util.List;

import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.ClassifySpRelation;
import cn.soa.entity.spareparts.SpClassify;

public interface SparePartTypeService {

	/**
	 * 获取设备备件分类树（树形结构）
	 * @return
	 */
	List<TreeObject> getSparepartsClassInfoAsTree();

	/**
	 * 
	 * 批量添加设备备件分类信息
	 * @param spClassifies
	 * @return
	 */
	String addSparepartsClassInfo(List<SpClassify> spClassifies);

	/**
	 * 
	 * 批量删除数据
	 * @param spClassifies
	 * @return
	 */
	String delSparepartsClassInfo(List<SpClassify> spClassifies);

	/**
	 * 更新设备备件分类数据
	 * @param spClassify
	 * @return
	 */
	String updateSparepartsClassInfo(SpClassify spClassify);

	/**
	 * 备件分类与备件关系添加
	 * @param classifySpRelations
	 * @return
	 */
	String addClassifySpRelation(List<ClassifySpRelation> classifySpRelations);

	/**
	 * 备件分类与备件关系删除
	 * @param classifySpRelations
	 * @return
	 */
	String delClassifySpRelation(List<ClassifySpRelation> classifySpRelations);

}
