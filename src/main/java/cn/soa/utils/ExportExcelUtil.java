package cn.soa.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import cn.soa.entity.LubricationMothlyReport;
import cn.soa.entity.LubricationRecordReport;
import lombok.extern.slf4j.Slf4j;

/**
 * 导出excel表格工具类
 * @author Jiang, Hang
 *
 */
@Slf4j
public class ExportExcelUtil {
	
	/**
	 * 导出润滑油月度记录表
	 * @year 年份
	 * @data 润滑油使用记录数据集合
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public static byte[] exportLubricationMonthlyReport(String year, List<LubricationMothlyReport> data) throws InvalidFormatException, IOException {
		log.info("------开始导出润滑油月度记录表------");
		if(year == null || data == null) {
			log.info("------年份或数据不能为空------");
			return null;
		}
		ClassPathResource resource = new ClassPathResource("/config/润滑油月度记录表.xlsx");
		
		//生成工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
		//生成sheet表
		XSSFSheet sheet = workbook.getSheetAt(0);
		//获取标题行
		XSSFRow titleRow = sheet.getRow(0);
		XSSFCell titleCell = titleRow.getCell(0);
		titleCell.setCellValue(year+"年润滑油月度记录表");
		//获取列数
		short lastCellNum = titleRow.getLastCellNum();
		
		//遍历行
		int rowNum = 2;
		double total = 0;
		String oid = null;
		XSSFRow row = sheet.getRow(rowNum);
		for(LubricationMothlyReport report : data) {

			if(oid == null) {
				oid = report.getOid();
				XSSFCell cell = row.getCell(0);
				cell.setCellValue(report.getOname());
			}
			if(oid.equals(report.getOid())) {
				//填充数据
				XSSFCell cell = row.getCell(Integer.parseInt(report.getMonth()));
				cell.setCellValue(report.getRamount());
				total += Double.parseDouble(report.getRamount());
			}else {
				oid = report.getOid();
				XSSFCell cell = row.getCell(13);
				cell.setCellValue(total);
				rowNum++;
				total = 0;
				row = sheet.getRow(rowNum);
				XSSFCell cell1 = row.getCell(Integer.parseInt(report.getMonth()));
				cell1.setCellValue(report.getRamount());
				XSSFCell cell2 = row.getCell(0);
				cell2.setCellValue(report.getOname());
			}
		}
		if(total != 0) {
			XSSFCell cell = row.getCell(13);
			cell.setCellValue(total);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		workbook.close();
		
		return bos.toByteArray();
	}
	
	
	/**
	 * 导出设备润滑油记录表
	 * @data 设备润滑油加油和换油记录数据集合
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public static byte[] exportLubricationRecordReport(List<LubricationRecordReport> data) throws InvalidFormatException, IOException {
		log.info("------开始导出设备润滑油记录表------");

		ClassPathResource resource = new ClassPathResource("/config/设备润滑油记录表.xlsx");
		
		//生成工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
		//生成sheet表
		XSSFSheet sheet = workbook.getSheetAt(0);

		//遍历行
		int rowNum = 3;
		for(LubricationRecordReport record : data) {
			XSSFRow row = sheet.createRow(rowNum);
			row.setHeightInPoints(28.0f);
			if(record.getPositionnum() != null) {
				XSSFCell cell = row.createCell(0);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(record.getPositionnum());
			}else {
				XSSFCell cell = row.createCell(0);
				cell.setCellStyle(getCellStyle(workbook));
			}
			if(record.getTname() != null) {
				XSSFCell cell = row.createCell(1);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(record.getTname());
			}else {
				XSSFCell cell = row.createCell(1);
				cell.setCellStyle(getCellStyle(workbook));
			}
			if(record.getOsign() != null) {
				XSSFCell cell = row.createCell(2);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(record.getOsign());
			}else {
				XSSFCell cell = row.createCell(2);
				cell.setCellStyle(getCellStyle(workbook));
			}
			if(record.getManufacture() != null) {
				XSSFCell cell = row.createCell(3);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(record.getManufacture());
			}else {
				XSSFCell cell = row.createCell(3);
				cell.setCellStyle(getCellStyle(workbook));
			}
			if(record.getOperatetype() != null) {
				if(Integer.parseInt(record.getOperatetype()) == 1) {
					XSSFCell cell = row.createCell(4);
					cell.setCellStyle(getCellStyle(workbook));
					cell.setCellValue(record.getRamount());
				}else {
					XSSFCell cell = row.createCell(4);
					cell.setCellStyle(getCellStyle(workbook));
				}

				if(Integer.parseInt(record.getOperatetype()) == 2) {
					XSSFCell cell = row.createCell(5);
					cell.setCellStyle(getCellStyle(workbook));
					cell.setCellValue(record.getRamount());
				}else {
					XSSFCell cell = row.createCell(5);
					cell.setCellStyle(getCellStyle(workbook));
				}
			}
			if(record.getPtime() != null) {
				XSSFCell cell = row.createCell(6);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(CommonUtil.dateFormat(record.getPtime()));
			}else {
				XSSFCell cell = row.createCell(6);
				cell.setCellStyle(getCellStyle(workbook));
			}
			if(record.getExcutor() != null) {
				XSSFCell cell = row.createCell(7);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(record.getExcutor());
			}else {
				XSSFCell cell = row.createCell(7);
				cell.setCellStyle(getCellStyle(workbook));
			}
			if(record.getRnote() != null) {
				XSSFCell cell = row.createCell(8);
				cell.setCellStyle(getCellStyle(workbook));
				cell.setCellValue(record.getRnote());
			}else {
				XSSFCell cell = row.createCell(8);
				cell.setCellStyle(getCellStyle(workbook));
			}
			
			rowNum++;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		workbook.close();
		
		return bos.toByteArray();
	}
	
	/**
	 * 获取单元格格式CellStyle
	 * @return
	 */
	private static XSSFCellStyle getCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		return style;
	}
}
