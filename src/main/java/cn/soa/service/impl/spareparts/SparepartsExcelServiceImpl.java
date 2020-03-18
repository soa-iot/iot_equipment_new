
/**
 * <一句话功能描述>
 * <p>设备备件相关excel导入导出服务层实现类
 * @author 陈宇林
 * @version [版本号, 2020年3月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.soa.dao.spareparts.EqOrSpRelationMapper;
import cn.soa.entity.spareparts.EqOrSpRelation;
import cn.soa.service.intel.spareparts.SparepartsExcelService;
import cn.soa.utils.ExcelTool;

@Service
public class SparepartsExcelServiceImpl implements SparepartsExcelService {

	@Autowired
	private EqOrSpRelationMapper eqOrSpRelationMapper;// 设备备件关系表mapper

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
					System.out.println(key + ":" + value);
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
		}

		return "成功导入了" + result + "条数据";
	}

}
