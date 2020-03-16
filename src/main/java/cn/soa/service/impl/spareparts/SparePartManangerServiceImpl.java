
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2020年3月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.EquipmentDisplayInfoMapper;
import cn.soa.dao.spareparts.EqOrSpRelationMapper;
import cn.soa.dao.spareparts.SpClassifyMapper;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.EqOrSpRelation;
import cn.soa.service.intel.spareparts.SparePartManangerService;

@Service
public class SparePartManangerServiceImpl implements SparePartManangerService {

	@Autowired
	private SpClassifyMapper spClassifyMapper;// 设备备件分类信息表mapper

	@Autowired
	private EquipmentCommonInfoMapper equipmentCommonInfoMapper;// 设备基本信息表mapper

	@Autowired
	private EquipmentDisplayInfoMapper equipmentDisplayInfoMapper;// 设备字段配置表mapper

	@Autowired
	private EqOrSpRelationMapper eqOrSpRelationMapper;// 设备与备件关系表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.spareparts.SparePartManangerService#
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
	 * cn.soa.service.intel.spareparts.SparePartManangerService#getEquInfo(cn.soa.
	 * entity.QueryCondition)
	 */
	@Override
	public Page<EquipmentCommonInfo> getEquInfo(QueryCondition condition) {

		Page<EquipmentCommonInfo> result = equipmentCommonInfoMapper
				.findBySelective(condition.getEquipmentCommonInfo());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartManangerService#getEquBaseColumn()
	 */
	@Override
	public List<EquipmentDisplayInfo> getEquBaseColumn(String equTypeId) {

		List<EquipmentDisplayInfo> result = equipmentDisplayInfoMapper.findAll();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartManangerService#addEquSpareRe(java.
	 * util.List)
	 */
	@Override
	@Transactional
	public String addEquSpareRe(List<EqOrSpRelation> eqOrSpRelations) {
		Integer result = 0;

		for (EqOrSpRelation item : eqOrSpRelations) {
			result += eqOrSpRelationMapper.insertSelective(item);
		}

		return "成功添加" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartManangerService#delEquSpareRe(java.
	 * util.List)
	 */
	@Override
	public String delEquSpareRe(List<EqOrSpRelation> eqOrSpRelations) {

		Integer result = eqOrSpRelationMapper.delByIdBatch(eqOrSpRelations);

		return "成功删除" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartManangerService#getEquSpareRe(java.
	 * lang.String)
	 */
	@Override
	public List<EqOrSpRelation> getEquSpareRe(String eqId) {
		
		List<EqOrSpRelation> result = eqOrSpRelationMapper.findByEqId(eqId);
		
		return result;
	}

}
