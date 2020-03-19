
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
import cn.soa.dao.spareparts.ClassifySpRelationMapper;
import cn.soa.dao.spareparts.EqOrSpRelationMapper;
import cn.soa.dao.spareparts.SpClassifyMapper;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.TreeObject;
import cn.soa.entity.spareparts.EqOrSpRelation;
import cn.soa.entity.spareparts.SpClassify;
import cn.soa.service.intel.spareparts.SparePartManangerService;

@Service
public class SparePartManangerServiceImpl implements SparePartManangerService {

	@Autowired
	private EquipmentCommonInfoMapper equipmentCommonInfoMapper;// 设备基本信息表mapper

	@Autowired
	private EquipmentDisplayInfoMapper equipmentDisplayInfoMapper;// 设备字段配置表mapper

	@Autowired
	private EqOrSpRelationMapper eqOrSpRelationMapper;// 设备与备件关系表mapper

	@Autowired
	private SpClassifyMapper spClassifyMapper;// 备件分类表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparePartManangerService#getEquInfo(cn.soa.
	 * entity.QueryCondition)
	 */
	@Override
	public Page<EquipmentCommonInfo> getEquInfo(QueryCondition condition) {

		Page<EquipmentCommonInfo> result = equipmentCommonInfoMapper.findBySelective(condition);

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

		List<EquipmentDisplayInfo> result = equipmentDisplayInfoMapper.findAllByEquType(equTypeId);

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
			// 关系表数据添加
			result += eqOrSpRelationMapper.insertSelective(item);
			/**
			 * 将相关数据加入到备件分类表
			 */
			
			SpClassify spClassify = new SpClassify();
			
			if ("0".equals(item.getType().toString())) {
				// 通过自定义关联
				spClassify.setClassifyValue(item.getCostomValue());
				spClassify.setClassifyName(item.getCostomValue());

			} else if ("1".equals(item.getType().toString())) {
				// 通过字段关联
				EquipmentCommonInfo equipmentCommonInfo = equipmentCommonInfoMapper.selectByPrimaryKey(item.getEqId());

				switch (item.getEqFieid().replaceAll("_", "").toUpperCase()) {
				case "EQUID":
					// 通过设备id关联
					spClassify.setClassifyValue(equipmentCommonInfo.getEquId());
					spClassify.setClassifyName(equipmentCommonInfo.getEquId());
					break;
				case "EQUNAME":
					// 通过设备名称关联
					spClassify.setClassifyValue(equipmentCommonInfo.getEquName());
					spClassify.setClassifyName(equipmentCommonInfo.getEquName());
					break;
				case "EQUSTATUS":
					// 通过设备状态关联
					spClassify.setClassifyValue(equipmentCommonInfo.getEquStatus());
					spClassify.setClassifyName(equipmentCommonInfo.getEquStatus());
					break;
				case "EQUPOSITIONNUM":
					// 通过设备位号关联
					spClassify.setClassifyValue(equipmentCommonInfo.getEquPositionNum());
					spClassify.setClassifyName(equipmentCommonInfo.getEquPositionNum());
					break;
				case "PROCESSUNITS":
					spClassify.setClassifyValue(equipmentCommonInfo.getProcessUnits());
					spClassify.setClassifyName(equipmentCommonInfo.getProcessUnits());
					break;
				case "EQUMODEL":
					spClassify.setClassifyValue(equipmentCommonInfo.getEquModel());
					spClassify.setClassifyName(equipmentCommonInfo.getEquModel());
					break;
				case "ASSETVALUE":
					spClassify.setClassifyValue(equipmentCommonInfo.getAssetValue());
					spClassify.setClassifyName(equipmentCommonInfo.getAssetValue());
					break;
				case "EQUMANUFACTURER":
					spClassify.setClassifyValue(equipmentCommonInfo.getEquManufacturer());
					spClassify.setClassifyName(equipmentCommonInfo.getEquManufacturer());
					break;
				case "EQUPRODUCDATE":
					spClassify.setClassifyValue(equipmentCommonInfo.getEquProducDate());
					spClassify.setClassifyName(equipmentCommonInfo.getEquProducDate());
					break;
				case "EQUCOMMISSIONDATE":
					spClassify.setClassifyValue(equipmentCommonInfo.getEquCommissionDate());
					spClassify.setClassifyName(equipmentCommonInfo.getEquCommissionDate());
					break;
				case "EQUINSTALLPOSITION":
					spClassify.setClassifyValue(equipmentCommonInfo.getEquInstallPosition());
					spClassify.setClassifyName(equipmentCommonInfo.getEquInstallPosition());
					break;
				case "STANDBY1":
					spClassify.setClassifyValue(equipmentCommonInfo.getStandby1());
					spClassify.setClassifyName(equipmentCommonInfo.getStandby1());
					break;
				case "STANDBY2":
					spClassify.setClassifyValue(equipmentCommonInfo.getStandby2());
					spClassify.setClassifyName(equipmentCommonInfo.getStandby2());
					break;
				case "STANDBY3":
					spClassify.setClassifyValue(equipmentCommonInfo.getStandby3());
					spClassify.setClassifyName(equipmentCommonInfo.getStandby3());
					break;
				default:
					break;
				}

			}
			//备件分类表新增数据
			spClassifyMapper.insertSelective(spClassify);

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
