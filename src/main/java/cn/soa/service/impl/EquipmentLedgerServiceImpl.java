
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2019年8月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;

import ch.qos.logback.core.joran.conditional.Condition;
import cn.soa.dao.EquipmentCommonInfoBackMapper;
import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.EquipmentDisplayInfoMapper;
import cn.soa.dao.EquipmentPropertiesBackMapper;
import cn.soa.dao.EquipmentPropertiesMapper;
import cn.soa.dao.EquipmentTypeMapper;
import cn.soa.dao.EquipmentTypeRMapper;
import cn.soa.entity.Column;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.EquipmentProperties;
import cn.soa.entity.EquipmentType;
import cn.soa.entity.EquipmentTypeR;
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

	@Autowired
	private EquipmentCommonInfoBackMapper equipmentCommonInfoBackMapper;

	@Autowired
	private EquipmentPropertiesBackMapper equipmentPropertiesBackMapper;

	@Autowired
	private EquipmentTypeRMapper equipmentTypeRMapper;

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

		// 插入通用属性
		Integer result = equipmentCommonInfoMapper.insertSelective(equipmentCommonInfo);

		// 插入设备、设备类型关系表
		String[] equTypeIds = equipmentCommonInfo.getEquTypeId().split(",");
		List<EquipmentTypeR> records = new ArrayList<EquipmentTypeR>();
		for (String equTypeId : equTypeIds) {
			EquipmentTypeR record = new EquipmentTypeR();
			record.setEquId(equipmentCommonInfo.getEquId());
			record.setEquTypeId(equTypeId);
			records.add(record);
		}

		equipmentTypeRMapper.addRecores(records);

		// 插入扩展属性
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
	public String updateEquipmentRecord(QueryCondition condition) {

		EquipmentCommonInfo equipmentCommonInfo = condition.getEquipmentCommonInfo();

		String backId = UUID.randomUUID().toString().replaceAll("-", "");

		condition.setUuid(backId);
		condition.setEquId(equipmentCommonInfo.getEquId());

		// 备份原有数据
		equipmentCommonInfoBackMapper.insertByCondition(condition);
		equipmentPropertiesBackMapper.insertByCondition(condition);

		// 修改数据
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
	public String delEquipmentRecord(QueryCondition condition) {

		List<EquipmentCommonInfo> equipmentCommonInfos = condition.getEquipmentList();

		/**
		 * 备份数据
		 */
		for (EquipmentCommonInfo equipmentCommonInfo : equipmentCommonInfos) {
			String backId = UUID.randomUUID().toString().replaceAll("-", "");
			condition.setUuid(backId);
			condition.setEquId(equipmentCommonInfo.getEquId());
			equipmentCommonInfoBackMapper.insertByCondition(condition);
			equipmentPropertiesBackMapper.insertByCondition(condition);
		}

		/**
		 * 删除数据
		 */
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

		// 表头数据
		List<EquipmentDisplayInfo> headers = equipmentDisplayInfoMapper.selectByCondition(condition);
		// 设备数据
		List<EquipmentCommonInfo> equipmentList = null;

		List<Map<String, Object>> equipmentMap = new ArrayList<Map<String, Object>>();

		// 多级表头的基数
		int classNum = 1;

		switch (condition.getExportType()) {
		case "1":
			// 空白模板
			Map<String, Object> coclunmMap = new HashMap<String, Object>();
			for (EquipmentDisplayInfo equipmentDisplayInfo : headers) {
				coclunmMap.put(equipmentDisplayInfo.getField(), equipmentDisplayInfo.getField());
				if (equipmentDisplayInfo.getClassNum() > classNum) {
					classNum = equipmentDisplayInfo.getClassNum();
				}
			}
			equipmentMap.add(coclunmMap);
			break;

		case "2":
			// 部分数据
		case "3":
			long currentminis = System.currentTimeMillis();
			equipmentList = equipmentCommonInfoMapper.selectByCondition(condition);
			System.out.println(System.currentTimeMillis() - currentminis + "ms");
			// 全部数据
			for (EquipmentCommonInfo equipmentInfo : equipmentList) {
				Map<String, Object> mapItem = new HashMap<String, Object>();
				mapItem.put("equId", equipmentInfo.getEquId());
				mapItem.put("equName", equipmentInfo.getEquName());
				mapItem.put("equStatus", equipmentInfo.getEquStatus());
				mapItem.put("equPositionNum", equipmentInfo.getEquPositionNum());
				mapItem.put("processUnits", equipmentInfo.getProcessUnits());
				mapItem.put("equModel", equipmentInfo.getEquModel());
				mapItem.put("equType", equipmentInfo.getEquType());
				mapItem.put("equTypeId", equipmentInfo.getEquTypeId());
				mapItem.put("assetValue", equipmentInfo.getAssetValue());
				mapItem.put("equManufacturer", equipmentInfo.getEquManufacturer());
				mapItem.put("equProducDate", equipmentInfo.getEquProducDate());
				mapItem.put("equCommissionDate", equipmentInfo.getEquCommissionDate());
				mapItem.put("equInstallPosition", equipmentInfo.getEquInstallPosition());
				for (EquipmentProperties property : equipmentInfo.getEquipmentProperties()) {
					mapItem.put(property.getProNameEn(), property.getProValue());
				}
				equipmentMap.add(mapItem);
			}
			break;
		default:
			break;
		}

		ExcelTool excelTool = new ExcelTool(condition.getEquType(), 20, 20);

		List<Column> titleData = excelTool.columnTransformer(headers, "id", "pId", "title", "field", "0");

		HSSFWorkbook hSSFWorkbook = excelTool.exportWorkbook(titleData, equipmentMap, true);

		/**
		 * 空白模板隐藏字段行
		 */
		if ("1".endsWith(condition.getExportType())) {
			HSSFSheet sheet = hSSFWorkbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(classNum + 1);
			row.setZeroHeight(true);
		}

		// 清空response
		response.reset();
		// 设置response的Header
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(condition.getEquType() + "台账.xls", "utf-8"));
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		// 将excel写入到输出流中
		hSSFWorkbook.write(os);
		os.flush();
		os.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.soa.service.intel.EquipmentLedgerService#importEquipment(org.
	 * springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	@Transactional
	public String importEquipment(MultipartFile exportFile, String equTypeId) throws Exception {
		if (exportFile.isEmpty()) {
			return "error：上传的文件为空，请检查后再上传！！！";
		}
		String fileName = exportFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!(suffix.equals("xlsx") || suffix.equals("xls"))) {
			return "error：上传的文件格式不正确，请检查后再上传！！！";
		}
		InputStream inputStream = exportFile.getInputStream();

		ExcelTool excelTool = new ExcelTool<>();

		// 动态获取文件类型
		Workbook workbook = excelTool.getWorkbookType(inputStream, fileName);
		inputStream.close();

		Sheet sheet = workbook.getSheetAt(0); // 第一个sheet
		int rowLength = sheet.getLastRowNum() + 1; // 总行数
		int colLength = sheet.getRow(0).getLastCellNum(); // 总列数

		// 获取多级表头的级数
		int maxClassNum = 1;

		QueryCondition condition = new QueryCondition();
		List<String> commonProperty = new ArrayList<String>();
		condition.setEquTypeId(equTypeId);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.EquipmentLedgerService#getSearchFormInfo(cn.soa.entity.
	 * QueryCondition)
	 */
	@Override
	public List<EquipmentDisplayInfo> getSearchFormInfo(QueryCondition condition) {

		List<EquipmentDisplayInfo> result = equipmentDisplayInfoMapper.selectSearchFormInfo(condition);

		return result;
	}

}
