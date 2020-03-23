
/**
 * <一句话功能描述>
 * <p>设备备件相关excel导入导出服务层实现类
 * @author 陈宇林
 * @version [版本号, 2020年3月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.spareparts.EqOrSpRelationMapper;
import cn.soa.dao.spareparts.SpClassifyMapper;
import cn.soa.dao.spareparts.SparePartMapper;
import cn.soa.entity.Column;
import cn.soa.entity.EquipmentCommonInfo;
import cn.soa.entity.EquipmentDisplayInfo;
import cn.soa.entity.EquipmentProperties;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.EqOrSpRelation;
import cn.soa.entity.spareparts.SpClassify;
import cn.soa.entity.spareparts.SparePart;
import cn.soa.entity.spareparts.SparePartColumn;
import cn.soa.service.intel.spareparts.SparepartsExcelService;
import cn.soa.utils.ExcelTool;

@Service
public class SparepartsExcelServiceImpl implements SparepartsExcelService {

	@Autowired
	private EqOrSpRelationMapper eqOrSpRelationMapper;// 设备备件关系表mapper

	@Autowired
	private SparePartMapper sparePartMapper;// 备件信息表mapper

	@Autowired
	private SpClassifyMapper spClassifyMapper;// 备件分类表mapper

	@Autowired
	private EquipmentCommonInfoMapper equipmentCommonInfoMapper;// 设备通用信息表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsExcelService#importEqOrSpRe(org.
	 * springframework.web.multipart.MultipartFile)
	 */
	@Override
	@Transactional
	public String importEqOrSpRe(MultipartFile exportFile) throws Exception {

		/**
		 * 执行导入前检查文件是否正确
		 */
		if (exportFile.isEmpty()) {
			return "error：上传的文件为空，请检查后再上传！！！";
		}
		String fileName = exportFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!(suffix.equals("xlsx") || suffix.equals("xls"))) {
			return "error：上传的文件格式不正确，请检查后再上传！！！";
		}

		/**
		 * 执行导入
		 */
		InputStream inputStream = exportFile.getInputStream();

		ExcelTool excelTool = new ExcelTool<>();

		// 动态获取文件类型
		Workbook workbook = excelTool.getWorkbookType(inputStream, fileName);
		inputStream.close();

		Sheet sheet = workbook.getSheetAt(0); // 第一个sheet
		int rowLength = sheet.getLastRowNum() + 1; // 总行数
		int colLength = sheet.getRow(0).getLastCellNum(); // 总列数

		// 存放excel读取的数据
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		// 循环行
		for (int rowIndex = 2; rowIndex < rowLength; rowIndex++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Row row = sheet.getRow(rowIndex);
			// 循环列
			for (int colIndex = 0; colIndex < colLength; colIndex++) {
				// System.out.println("colIndex" + colIndex);
				Cell cell = row.getCell(colIndex);
				String value = excelTool.getCellFormatValue(cell);
				String key = excelTool.getCellFormatValue(sheet.getRow(1).getCell(colIndex));

				if (key != null && !"".equals(key)) {
					dataMap.put(key, value);
				}

			}
			dataList.add(dataMap);
		}

		Integer result = 0;
		for (Map<String, Object> item : dataList) {
			EqOrSpRelation eqOrSpRelation = new EqOrSpRelation();
			Set<String> keys = item.keySet();
			for (String key : keys) {
				eqOrSpRelation.setProperty(key, String.valueOf(item.get(key)));
			}
			// 导入数据
			result += eqOrSpRelationMapper.insertSelective(eqOrSpRelation);

			/**
			 * 将相关数据加入到备件分类表
			 */

			SpClassify spClassify = new SpClassify();

			if ("0".equals(eqOrSpRelation.getType().toString())) {
				// 通过自定义关联
				spClassify.setClassifyValue(eqOrSpRelation.getCostomValue());
				spClassify.setClassifyName(eqOrSpRelation.getCostomValue());

			} else if ("1".equals(eqOrSpRelation.getType().toString())) {
				// 通过字段关联
				EquipmentCommonInfo equipmentCommonInfo = equipmentCommonInfoMapper
						.selectByPrimaryKey(eqOrSpRelation.getEqId());

				switch (eqOrSpRelation.getEqFieid().replaceAll("_", "").toUpperCase()) {
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
			// 备件分类表新增数据
			spClassifyMapper.insertSelective(spClassify);

		}

		return "成功导入了" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsExcelService#importSparepart(org.
	 * springframework.web.multipart.MultipartFile)
	 */
	@Override
	public String importSparepart(MultipartFile exportFile) throws Exception {
		/**
		 * 执行导入前检查文件是否正确
		 */
		if (exportFile.isEmpty()) {
			return "error：上传的文件为空，请检查后再上传！！！";
		}
		String fileName = exportFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!(suffix.equals("xlsx") || suffix.equals("xls"))) {
			return "error：上传的文件格式不正确，请检查后再上传！！！";
		}

		/**
		 * 执行导入
		 */
		InputStream inputStream = exportFile.getInputStream();

		ExcelTool excelTool = new ExcelTool<>();

		// 动态获取文件类型
		Workbook workbook = excelTool.getWorkbookType(inputStream, fileName);
		inputStream.close();

		Sheet sheet = workbook.getSheetAt(0); // 第一个sheet
		int rowLength = sheet.getLastRowNum() + 1; // 总行数
		int colLength = sheet.getRow(0).getLastCellNum(); // 总列数


		// 存放excel读取的数据
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		// 循环行
		for (int rowIndex = 2; rowIndex < rowLength; rowIndex++) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Row row = sheet.getRow(rowIndex);
			// 循环列
			for (int colIndex = 0; colIndex < colLength; colIndex++) {
				// System.out.println("colIndex" + colIndex);
				Cell cell = row.getCell(colIndex);
				String value = excelTool.getCellFormatValue(cell);
				String key = excelTool.getCellFormatValue(sheet.getRow(1).getCell(colIndex));

				if (key != null && !"".equals(key)) {
					dataMap.put(key, value);
				}

			}
			dataList.add(dataMap);
		}

		Integer result = 0;
		for (Map<String, Object> item : dataList) {
			SparePart sparePart = new SparePart();
			Set<String> keys = item.keySet();
			for (String key : keys) {
				sparePart.setProperty(key, String.valueOf(item.get(key)));
			}
			// 导入数据
			result += sparePartMapper.insertSelective(sparePart);
		}

		return "成功导入了" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsExcelService#exportEquipment(cn.soa
	 * .entity.QueryCondition, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void exportEquipment(QueryCondition condition, HttpServletResponse response) throws Exception {

		List<SparePart> sparePartList = sparePartMapper.findByCondition(condition);

		List<Map<String, String>> headers = new ArrayList<>();
		/**
		 * 构造表头
		 */
		Map<String, String> map1 = new HashMap<>();
		map1.put("备件编码", "spEncoding");
		headers.add(map1);

		Map<String, String> map2 = new HashMap<>();
		map2.put("备件名称", "spName");
		headers.add(map2);
		Map<String, String> map3 = new HashMap<>();
		map3.put("品牌", "brand");
		headers.add(map3);
		Map<String, String> map4 = new HashMap<>();
		map4.put("分类", "type");
		headers.add(map4);
		Map<String, String> map5 = new HashMap<>();
		map5.put("规格", "specification");
		headers.add(map5);
		Map<String, String> map6 = new HashMap<>();
		map6.put("单位", "unit");
		headers.add(map6);
		Map<String, String> map7 = new HashMap<>();
		map7.put("单价", "unitCost");
		headers.add(map7);
		Map<String, String> map8 = new HashMap<>();
		map8.put("备件库存", "spInventory");
		headers.add(map8);
		Map<String, String> map9 = new HashMap<>();
		map9.put("备件预警值", "prewarningVal");
		headers.add(map9);
		Map<String, String> map10 = new HashMap<>();
		map10.put("采购周期", "procurementCycle");
		headers.add(map10);
		Map<String, String> map11 = new HashMap<>();
		map11.put("标签码", "labelCode");
		headers.add(map11);
		Map<String, String> map12 = new HashMap<>();
		map12.put("生产厂家", "manufactureFactory");
		headers.add(map12);
		Map<String, String> map13 = new HashMap<>();
		map13.put("生产日期", "productionDate");
		headers.add(map13);

		List<Map<String, String>> dataMap = new ArrayList<>();

		for (SparePart sparePart : sparePartList) {
			Map<String, String> itemMap = new HashMap<>();
			itemMap.put("spEncoding", sparePart.getSpEncoding());
			itemMap.put("spName", sparePart.getSpName());
			itemMap.put("brand", sparePart.getBrand());
			itemMap.put("type", sparePart.getType());
			itemMap.put("specification", sparePart.getSpecification());
			itemMap.put("unit", sparePart.getUnit());
			itemMap.put("unitCost", sparePart.getUnitCost() + "");
			itemMap.put("spInventory", sparePart.getSpInventory() + "");
			itemMap.put("prewarningVal", sparePart.getPrewarningVal() + "");
			itemMap.put("procurementCycle", sparePart.getProcurementCycle());
			itemMap.put("labelCode", sparePart.getLabelCode());
			itemMap.put("manufactureFactory", sparePart.getManufactureFactory());
			itemMap.put("productionDate", sparePart.getProductionDate());
			dataMap.add(itemMap);
		}
		System.out.println(dataMap);

		ExcelTool excelTool = new ExcelTool("备件台账", 20, 20);

		System.out.println(headers);

		List<Column> titleData = excelTool.columnTransformer(headers);

		System.out.println(titleData);

		HSSFWorkbook hSSFWorkbook = excelTool.exportWorkbook(titleData, dataMap, true);

		// 清空response
		response.reset();
		// 设置response的Header
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("备件台账.xls", "utf-8"));
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		// 将excel写入到输出流中
		hSSFWorkbook.write(os);
		os.flush();
		os.close();
	}

}
