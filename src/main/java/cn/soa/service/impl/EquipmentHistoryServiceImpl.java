
/**
 * <一句话功能描述>
 * <p>设备历史操作/版本数据处理业务层
 * @author 陈宇林
 * @version [版本号, 2019年9月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.EquBeforeImportBackMapper;
import cn.soa.dao.EquipmentCommonInfoBackMapper;
import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.EquipmentDisplayInfoMapper;
import cn.soa.dao.EquipmentPropertiesMapper;
import cn.soa.dao.EquipmentTypeRMapper;
import cn.soa.entity.EquBeforeImportBack;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentCommonInfoBack;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.EquipmentProperties;
import cn.soa.entity.EquipmentTypeR;
import cn.soa.entity.QueryCondition;
import cn.soa.service.intel.EquipmentHistoryService;
import cn.soa.utils.ExcelTool;

@Service
public class EquipmentHistoryServiceImpl implements EquipmentHistoryService {

	@Autowired
	private EquipmentCommonInfoBackMapper equipmentCommonInfoBackMapper;

	@Autowired
	private EquipmentCommonInfoMapper equipmentCommonInfoMapper;
	@Autowired
	private EquipmentPropertiesMapper equipmentPropertiesMapper;

	@Autowired
	private EquBeforeImportBackMapper equBeforeImportBackMapper;

	@Autowired
	private EquipmentTypeRMapper equipmentTypeRMapper;

	@Autowired
	private EquipmentDisplayInfoMapper equipmentDisplayInfoMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentHistoryService#getEquHistoryList(cn.soa.entity.
	 * QueryCondition)
	 */
	@Override
	public Page<EquipmentCommonInfoBack> getEquHistoryList(QueryCondition condition) {

		Page<EquipmentCommonInfoBack> result = equipmentCommonInfoBackMapper.selectByCondition(condition);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentHistoryService#recoveryEquInfo(cn.soa.entity.
	 * QueryCondition)
	 */
	@Override
	@Transactional
	public String recoveryEquInfo(QueryCondition condition) {

		/**
		 * 删除需要回复的数据
		 */
		equipmentCommonInfoMapper.deleteByPrimaryKey(condition.getEquId());
		equipmentPropertiesMapper.deleteByCondition(condition);

		/**
		 * 恢复设备通用属性
		 */
		int result = equipmentCommonInfoMapper.recoveryEquInfo(condition);

		// 恢复扩展属性
		equipmentPropertiesMapper.recoveryEquInfo(condition);

		return result + "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentHistoryService#getExcelBackRecord(cn.soa.entity
	 * .QueryCondition)
	 */
	@Override
	public Page<EquBeforeImportBack> getExcelBackRecord(QueryCondition condition) {

		Page<EquBeforeImportBack> result = equBeforeImportBackMapper.selectByCondition(condition);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentHistoryService#recoveryExcelBackRecord(cn.soa.
	 * entity.QueryCondition)
	 */
	@Override
	@Transactional
	public String recoveryExcelBackRecord(QueryCondition condition) throws Exception {

		EquBeforeImportBack equBeforeImportBack = equBeforeImportBackMapper.selectByPrimaryKey(condition.getBackId());

		String backFilePathReal = equBeforeImportBack.getBackFilePathReal();

		String equTypeId = condition.getEquTypeId();

		equipmentPropertiesMapper.deleteByEquTypeId(equTypeId);

		equipmentCommonInfoMapper.deleteByEquTypeId(equTypeId);

		File backFile = new File(backFilePathReal);
		String fileName = backFile.getName();
		backFile.setWritable(true);// 设置可读

		InputStream inputStream = new FileInputStream(backFile);

		String result = importExcelData(inputStream, fileName, equTypeId, condition);

		return result;
	}

	@Transactional
	public String importExcelData(InputStream inputStream, String fileName, String equTypeId, QueryCondition condition)
			throws Exception {

		ExcelTool excelTool = new ExcelTool<>();

		// 动态获取文件类型
		Workbook workbook = excelTool.getWorkbookType(inputStream, fileName);
		inputStream.close();

		Sheet sheet = workbook.getSheetAt(0); // 第一个sheet
		int rowLength = sheet.getLastRowNum() + 1; // 总行数
		int colLength = sheet.getRow(0).getLastCellNum(); // 总列数

		// 获取多级表头的级数
		int maxClassNum = 1;

		List<String> commonProperty = new ArrayList<String>();
		// 获取字段信息
		List<EquipmentDisplayInfo> equipmentDisplayInfos = equipmentDisplayInfoMapper.selectByCondition(condition);
		for (EquipmentDisplayInfo equipmentDisplayInfo : equipmentDisplayInfos) {
			if (maxClassNum < equipmentDisplayInfo.getClassNum()) {
				maxClassNum = equipmentDisplayInfo.getClassNum();
			}
			if ("1".equals(equipmentDisplayInfo.getPropertyType() + "")) {
				// 通用属性
				commonProperty.add(equipmentDisplayInfo.getField());
			}
		}

		// 存放excel读取的数据
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		// 循环行
		for (int rowIndex = maxClassNum + 2; rowIndex < rowLength; rowIndex++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Row row = sheet.getRow(rowIndex);
			// 循环列
			for (int colIndex = 0; colIndex < colLength; colIndex++) {
				// System.out.println("colIndex" + colIndex);
				Cell cell = row.getCell(colIndex);
				String value = excelTool.getCellFormatValue(cell);
				String key = excelTool.getCellFormatValue(sheet.getRow(maxClassNum + 1).getCell(colIndex));
				System.out.println(key + "," + value);
				dataMap.put(key, value);

			}
			// System.out.println(dataMap);
			dataList.add(dataMap);
		}

		List<EquipmentCommonInfo> equipmentList = new ArrayList<EquipmentCommonInfo>();

		List<EquipmentTypeR> equipmentTypeRList = new ArrayList<EquipmentTypeR>();
		// 构造设备对象
		for (Map<String, Object> item : dataList) {
			EquipmentCommonInfo equipmentCommonInfo = new EquipmentCommonInfo();
			EquipmentTypeR equipmentTypeR = new EquipmentTypeR();
			List<EquipmentProperties> equipmentPropertiesList = new ArrayList<EquipmentProperties>();
			equipmentCommonInfo.setEquId(UUID.randomUUID().toString().replaceAll("-", ""));
			equipmentCommonInfo.setEquTypeId(equTypeId);

			/**
			 * 关系表
			 */
			equipmentTypeR.setEquId(equipmentCommonInfo.getEquId());
			equipmentTypeR.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			equipmentTypeR.setEquTypeId(equTypeId);
			equipmentTypeRList.add(equipmentTypeR);

			Set<String> keys = item.keySet();
			for (String key : keys) {
				if (commonProperty.contains(key)) {
					// 通用属性
					equipmentCommonInfo.setProperty(key, (String) item.get(key));
				} else {
					// 扩展属性
					EquipmentProperties equipmentProperties = new EquipmentProperties();
					equipmentProperties.setEquId(equipmentCommonInfo.getEquId());
					equipmentProperties.setProNameCn("");
					equipmentProperties.setProNameEn(key);
					equipmentProperties.setProValue((String) item.get(key));
					equipmentPropertiesList.add(equipmentProperties);
				}

			}
			equipmentCommonInfo.setEquipmentProperties(equipmentPropertiesList);

			equipmentList.add(equipmentCommonInfo);
		}
		// System.out.println(equipmentList);

		Integer result = 0;
		// 将数据插入数据库
		for (EquipmentCommonInfo equipmentCommonInfo : equipmentList) {
			result += equipmentCommonInfoMapper.insertSelective(equipmentCommonInfo);
			List<EquipmentProperties> equipmentPropertiesList = equipmentCommonInfo.getEquipmentProperties();
			for (EquipmentProperties equipmentProperties : equipmentPropertiesList) {
				equipmentPropertiesMapper.insertSelective(equipmentProperties);
			}
		}

		equipmentTypeRMapper.addRecores(equipmentTypeRList);

		String msg = "成功导入了" + result + "条设备数据";
		return msg;
	}

}
