
/**
 * <一句话功能描述>
 * <p>设备备件分类服务层实现类
 * @author 陈宇林
 * @version [版本号, 2020年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.spareparts.ClassifySpRelationMapper;
import cn.soa.dao.spareparts.SpClassifyMapper;
import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.ClassifySpRelation;
import cn.soa.entity.spareparts.SpClassify;
import cn.soa.service.intel.spareparts.SparePartTypeService;

@Service
public class SparePartTypeServiceImpl implements SparePartTypeService {

	@Autowired
	private SpClassifyMapper spClassifyMapper;// 设备备件分类信息表mapper

	@Autowired
	private ClassifySpRelationMapper classifySpRelationMapper;// 备件分类与备件关系表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.spareparts.SparePartTypeService#
	 * getSparepartsClassInfoAsTree()
	 */

	@Override
	public List<TreeObject> getSparepartsClassInfoAsTree() {

		List<TreeObject> result = spClassifyMapper.findAsTree();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartTypeService#addSparepartsClassInfo(
	 * java.util.List)
	 */
	@Override
	public String addSparepartsClassInfo(List<SpClassify> spClassifies) {

		Integer result = 0;

		for (SpClassify spClassifie : spClassifies) {
			result += spClassifyMapper.insertSelective(spClassifie);
		}
		return "成功插入" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartTypeService#delSparepartsClassInfo(
	 * java.util.List)
	 */
	@Override
	public String delSparepartsClassInfo(List<SpClassify> spClassifies) {

		Integer result = spClassifyMapper.delBatch(spClassifies);

		return "成功删除" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.spareparts.SparePartTypeService#
	 * updateSparepartsClassInfo(cn.soa.entity.spareparts.SpClassify)
	 */
	@Override
	public String updateSparepartsClassInfo(SpClassify spClassify) {

		Integer result = spClassifyMapper.updateSelective(spClassify);

		return "成功更新" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartTypeService#addClassifySpRelation(
	 * java.util.List)
	 */
	@Override
	@Transactional
	public String addClassifySpRelation(List<ClassifySpRelation> classifySpRelations) {
		
		Integer result = 0;

		for (ClassifySpRelation classifySpRelation : classifySpRelations) {
			result += classifySpRelationMapper.insertSelective(classifySpRelation);
		}
		return "成功添加" + result + "条数据";
	}

	/* (non-Javadoc)
	 * @see cn.soa.service.intel.spareparts.SparePartTypeService#delClassifySpRelation(java.util.List)
	 */
	@Override
	@Transactional
	public String delClassifySpRelation(List<ClassifySpRelation> classifySpRelations) {
		
		Integer result = classifySpRelationMapper.delBatch(classifySpRelations);
		
		return "成功删除" + result + "条数据";
	}

}
