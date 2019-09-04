
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2019年8月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.EquipmentDisplayInfoMapper;
import cn.soa.dao.EquipmentPropertiesMapper;
import cn.soa.dao.EquipmentTypeMapper;
import cn.soa.entity.Column;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.EquipmentProperties;
import cn.soa.entity.EquipmentType;
import cn.soa.entity.QueryCondition;
import cn.soa.service.intel.EquipmentLedgerService;
import cn.soa.utils.ExcelTool;

@Service
public class EquipmentLedgerServiceImpl implements EquipmentLedgerService {

	@Autowired
	private EquipmentTypeMapper equipmentTypeMapper;

	@Autowired
	private EquipmentDisplayInfoMapper equipmentDisplayInfoMapper;

	@Autowired
	private EquipmentCommonInfoMapper equipmentCommonInfoMapper;

	@Autowired
	private EquipmentPropertiesMapper equipmentPropertiesMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.EquipmentLedgerService#getAllEquipmentType()
	 */
	@Override
	public List<EquipmentType> getAllEquipmentType() {

		List<EquipmentType> result = equipmentTypeMapper.selectAllWithChidren();

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#getEquipmentDisplayInfo(cn.soa.
	 * entity.QueryCondition)
	 */
	@Override
	public List<EquipmentDisplayInfo> getEquipmentDisplayInfo(QueryCondition condition) {

		List<EquipmentDisplayInfo> result = equipmentDisplayInfoMapper.selectByCondition(condition);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#getEquipmentList(cn.soa.entity.
	 * QueryCondition)
	 */
	@Override
	public Page<EquipmentCommonInfo> getEquipmentList(QueryCondition condition) {

		Page<EquipmentCommonInfo> result = equipmentCommonInfoMapper.selectByCondition(condition);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.EquipmentLedgerService#getFormInfo()
	 */
	@Override
	public List<EquipmentDisplayInfo> getFormInfo(QueryCondition condition) {

		List<EquipmentDisplayInfo> result = equipmentDisplayInfoMapper.selectFormInfoByCondition(condition);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#addEquipmentRecord(cn.soa.entity.
	 * EquipmentCommonInfo)
	 */
	@Override
	@Transactional
	public String addEquipmentRecord(EquipmentCommonInfo equipmentCommonInfo) {

		Integer result = equipmentCommonInfoMapper.insertSelective(equipmentCommonInfo);
		List<EquipmentProperties> equipmentPropertiesList = equipmentCommonInfo.getEquipmentProperties();
		for (EquipmentProperties equipmentProperties : equipmentPropertiesList) {
			equipmentPropertiesMapper.insertSelective(equipmentProperties);
		}

		return result + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#updateEquipmentRecord(cn.soa.
	 * entity.EquipmentCommonInfo)
	 */
	@Transactional
	@Override
	public String updateEquipmentRecord(EquipmentCommonInfo equipmentCommonInfo) {
		Integer result = equipmentCommonInfoMapper.updateByPrimaryKeySelective(equipmentCommonInfo);

		List<EquipmentProperties> equipmentPropertiesList = equipmentCommonInfo.getEquipmentProperties();

		for (EquipmentProperties equipmentProperties : equipmentPropertiesList) {

			equipmentPropertiesMapper.updateSelective(equipmentProperties);
		}

		return result + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#delEquipmentRecord(cn.soa.entity.
	 * EquipmentCommonInfo)
	 */
	@Override
	@Transactional
	public String delEquipmentRecord(List<EquipmentCommonInfo> equipmentCommonInfos) {

		Integer result = equipmentCommonInfoMapper.delRecordByIds(equipmentCommonInfos);
		equipmentPropertiesMapper.delByEquIds(equipmentCommonInfos);

		return result + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#exportEquipment(cn.soa.entity.
	 * QueryCondition, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void exportEquipment(QueryCondition condition, HttpServletResponse response) throws Exception {

		switch (condition.getExportType()) {
		case "1":
			// 空白模板

			break;

		case "2":
			// 部分数据

			break;
		case "3":

			break;

		default:
			break;
		}

		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> dataListMap = new HashMap<String, Object>();
		dataListMap.put("item1", "888888");
		dataList.add(dataListMap);
		
		List<EquipmentDisplayInfo> headers = equipmentDisplayInfoMapper.selectByCondition(condition);

		ExcelTool<EquipmentDisplayInfo> excelTool = new ExcelTool<EquipmentDisplayInfo>("实体类（entity）数据 多级表头表格", 20, 20);

		List<Column> titleData = excelTool.columnTransformer(headers, "id", "pId", "title", "field", "0");
		
		excelTool.exportExcel(titleData,null,"D://outExcel1.xls",true,true);

	}

}
