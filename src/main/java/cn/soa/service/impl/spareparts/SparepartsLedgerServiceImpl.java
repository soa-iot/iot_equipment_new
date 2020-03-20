
/**
 * <一句话功能描述>
 * <p>备件台账服务层实现类
 * @author 陈宇林
 * @version [版本号, 2020年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.spareparts.EqOrSpRelationMapper;
import cn.soa.dao.spareparts.SparePartMapper;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.EqOrSpRelation;
import cn.soa.entity.spareparts.SparePart;
import cn.soa.exception.CommonException;
import cn.soa.exception.ParameterNotDiscernmentException;
import cn.soa.service.intel.spareparts.SparepartsLedgerService;

@Service
public class SparepartsLedgerServiceImpl implements SparepartsLedgerService {

	@Autowired
	private SparePartMapper sparePartMapper;// 设备备件信息表mapper

	@Autowired
	private EqOrSpRelationMapper eqOrSpRelationMapper;// 设备与备件关系表mapper

	@Autowired
	private EquipmentCommonInfoMapper equipmentCommonInfoMapper;// 设备基本信息表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#getSparePartsInfo(cn.
	 * soa.entity.QueryCondition)
	 */
	@Override
	public Page<SparePart> getSparePartsInfo(QueryCondition condition) {

		Page<SparePart> result = sparePartMapper.findByCondition(condition);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#delSparePartsInfo(
	 * java.util.List)
	 */
	@Override
	@Transactional
	public String delSparePartsInfo(List<SparePart> spareParts) {

		Integer result = sparePartMapper.delAsBatch(spareParts);

		return "成功删除" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#updateSparePartsInfo(
	 * cn.soa.entity.spareparts.SparePart)
	 */
	@Override
	public String updateSparePartsInfo(SparePart sparePart) {

		Integer result = sparePartMapper.updateSelective(sparePart);

		return "成功更新" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#addSparePartsInfo(
	 * java.util.List)
	 */
	@Override
	@Transactional
	public String addSparePartsInfo(List<SparePart> spareParts) {

		Integer result = 0;
		for (SparePart sparePart : spareParts) {

			result += sparePartMapper.insertSelective(sparePart);

		}

		return "成功添加" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.spareparts.SparepartsLedgerService#
	 * getSparePartsInfoByEquId(cn.soa.entity.QueryCondition)
	 */
	@Override
	public Page<SparePart> getSparePartsInfoByEquId(QueryCondition condition) throws Exception {

		// 设备id为null或者空字符串时，报异常
		if (condition.getEquId() == null || "".equals(condition.getEquId())) {
			throw new ParameterNotDiscernmentException("参数equId为空或null，请上传此参数");
		}

		// 备件分类值
		List<String> columnValues = new ArrayList<String>();

		// 获取设备与备件如何关联
		List<EqOrSpRelation> eqOrSpRelationList = eqOrSpRelationMapper.findByEqId(condition.getEquId());

		// 循环获取数据
		for (EqOrSpRelation eqOrSpRelation : eqOrSpRelationList) {
			// 关联类型
			BigDecimal type = eqOrSpRelation.getType();
			if (type == null) {
				continue;
			}
			switch (type.intValue()) {
			case 0:
				// 通过自定义值关联
				columnValues.add(eqOrSpRelation.getCostomValue());
				break;
			case 1:
				// 通过字段关联
				EquipmentCommonInfo equipmentCommonInfo = equipmentCommonInfoMapper
						.selectByPrimaryKey(condition.getEquId());
				String columnValue = equipmentCommonInfo.getProperty(eqOrSpRelation.getEqFieid());
				columnValues.add(columnValue);
				break;
			default:
				break;
			}
		}

		System.out.println(columnValues);
		
		if (columnValues.isEmpty()) {
			throw new CommonException("该设备未关联任何备件");
		}

		Page<SparePart> result = sparePartMapper.findBySpTypeValues(columnValues);

		return result;
	}

}
