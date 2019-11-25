package cn.soa.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.soa.entity.EquipmentMoveInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 导出动设备检维修excel表格
 * @author Jiang, Hang
 *
 */
@Slf4j
public class ExportMaintenanceExcel {
	
	/*public static void main(String[] args) throws IOException {
		
		List<EquipmentMoveInfo> list = new ArrayList<EquipmentMoveInfo>();
		EquipmentMoveInfo info1 = new EquipmentMoveInfo();
		info1.setComment("111");
		info1.setEquipName("设备1");
		info1.setEquipVersion("型号1");
		info1.setPositionNum("位号1");
		info1.setMaintenanceContent("内容1");
		info1.setMaintenancePerson("人员1");
		info1.setMaintenanceReason("原因1");
		info1.setMaintenanceTime(new Date());
		info1.setSpecificationAndNumber("前后端轴承:2;机械密封:2;油封:1");
		
		EquipmentMoveInfo info2 = new EquipmentMoveInfo();
		info2.setComment("222");
		info2.setMaintenanceContent("内容2");
		info2.setMaintenancePerson("人员2");
		info2.setMaintenanceReason("原因2");
		info2.setMaintenanceTime(new Date());
		info2.setSpecificationAndNumber("前端轴承:11;后端轴承:222");
		
		EquipmentMoveInfo info3 = new EquipmentMoveInfo();
		info3.setComment("333");
		info3.setMaintenanceContent("内容3");
		info3.setMaintenancePerson("人员3");
		info3.setMaintenanceReason("原因3");
		info3.setMaintenanceTime(new Date());
		info3.setSpecificationAndNumber("叶轮:2");
		
		list.add(info1);
		list.add(info2);
		list.add(info3);
		
		exportReport("1111", list);
	}*/

	public static ByteArrayOutputStream exportReport(String positionNum, List<EquipmentMoveInfo> list) throws IOException {
		log.info("------开始创建动设备检维修excel表------");
		log.info("------设备位号：{}", positionNum);
		log.info("------检维修信息：{}", list);
		if(positionNum == null || list == null || list.size() == 0) {
			log.info("------创建动设备检维修excel表失败，传入的设备位号或检维修信息不合法------");
			return null;
		}

		//生成工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//生成sheet表
		XSSFSheet sheet = workbook.createSheet(positionNum);
		//设置列宽
		sheet.setColumnWidth(0, 6*256);
		sheet.setColumnWidth(1, 15*256);
		sheet.setColumnWidth(2, 21*256);
		sheet.setColumnWidth(3, 21*256);
		sheet.setColumnWidth(4, 12*256);
		sheet.setColumnWidth(5, 11*256);
		sheet.setColumnWidth(6, 11*256);
		sheet.setColumnWidth(7, 16*256);
		sheet.setColumnWidth(8, 16*256);
		//生成标题行
		XSSFRow titleRow = sheet.createRow(0);
		//标题行合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 8));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 8));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 6));
		
		//添加标题内容
		XSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("重庆天然气净化总厂遂宁龙王庙天然气净化厂动设备检修记录");
		//设置单元格样式和字体大小
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont titleFont = workbook.createFont();
		titleFont.setFontName("宋体");
		titleFont.setFontHeightInPoints((short)17);
		titleFont.setBold(true);
		titleStyle.setFont(titleFont);
		//添加单元格样式
		titleCell.setCellStyle(titleStyle);
		
		//设置文档标号
		XSSFRow row4 = sheet.createRow(3);
		XSSFCell row4cell = row4.createCell(7);
		row4cell.setCellValue("SL/QHSE.WX06");
		row4cell.setCellStyle(getCellStyle(workbook, false, false));
		
		//设置 名称，位号，型号 行
		XSSFRow row5 = sheet.createRow(4);
		row5.setHeightInPoints(24);
		XSSFCell row5cell1 = row5.createCell(0);
		row5cell1.setCellValue("名称");
		row5cell1.setCellStyle(getCellStyle(workbook, true, true));
		
		XSSFCell row5cell2 = row5.createCell(1);
		row5cell2.setCellValue((list.get(0).getEquipName()==null?"":list.get(0).getEquipName()));
		row5cell2.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row5cell3 = row5.createCell(2);
		row5cell3.setCellValue("");
		row5cell3.setCellStyle(getCellStyle(workbook, false, true));

		XSSFCell row5cell4 = row5.createCell(3);
		row5cell4.setCellValue("位号");
		row5cell4.setCellStyle(getCellStyle(workbook, true, true));
		
		XSSFCell row5cell5 = row5.createCell(4);
		row5cell5.setCellValue((list.get(0).getPositionNum()==null?"":list.get(0).getPositionNum()));
		row5cell5.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row5cell6 = row5.createCell(5);
		row5cell6.setCellValue("");
		row5cell6.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row5cell7 = row5.createCell(6);
		row5cell7.setCellValue("型号");
		row5cell7.setCellStyle(getCellStyle(workbook, true, true));
		
		XSSFCell row5cell8 = row5.createCell(7);
		row5cell8.setCellValue((list.get(0).getEquipVersion()==null?"":list.get(0).getEquipVersion()));
		row5cell8.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row5cell9 = row5.createCell(8);
		row5cell9.setCellStyle(getCellStyle(workbook, false, true));
		
		//设置 序号,检修时间,检修原因,检修内容,更换备品备件型号规格及数量,检修人员,备注 行
		XSSFRow row6 = sheet.createRow(5);
		row6.setHeightInPoints(24);
		XSSFCell row6cell1 = row6.createCell(0);
		row6cell1.setCellValue("序号");
		row6cell1.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell2 = row6.createCell(1);
		row6cell2.setCellValue("检修时间");
		row6cell2.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell3 = row6.createCell(2);
		row6cell3.setCellValue("检修原因");
		row6cell3.setCellStyle(getCellStyle(workbook, false, true));

		XSSFCell row6cell4 = row6.createCell(3);
		row6cell4.setCellValue("检修内容");
		row6cell4.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell5 = row6.createCell(4);
		row6cell5.setCellValue("更换备品备件型号规格及数量");
		row6cell5.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell6 = row6.createCell(5);
		row6cell6.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell7 = row6.createCell(6);
		row6cell7.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell8 = row6.createCell(7);
		row6cell8.setCellValue("检修人员");
		row6cell8.setCellStyle(getCellStyle(workbook, false, true));
		
		XSSFCell row6cell9 = row6.createCell(8);
		row6cell9.setCellValue("备注");
		row6cell9.setCellStyle(getCellStyle(workbook, false, true));
		
		//生成内容行
		for(int i=0;i<list.size();i++) {
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(6+i, 6+i, 4, 6));
			
			EquipmentMoveInfo info = list.get(i);
			XSSFRow row = sheet.createRow(6+i);
			row.setHeightInPoints(75);
			XSSFCell cell1 = row.createCell(0);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell2 = row.createCell(1);
			cell2.setCellValue(CommonUtil.dateFormat(info.getMaintenanceTime()));
			cell2.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell3 = row.createCell(2);
			cell3.setCellValue((info.getMaintenanceReason()==null?"":info.getMaintenanceReason()));
			cell3.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell4 = row.createCell(3);
			cell4.setCellValue((info.getMaintenanceContent()==null?"":info.getMaintenanceContent()));
			cell4.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell5 = row.createCell(4);
			//解析 更换备品备件型号规格及数量
			String value = info.getSpecificationAndNumber();
			if(value == null || "".equals(value)) {
				cell5.setCellValue("");
			}else {
				String[] str = value.split(";");
				String strValue = "";
				for(String s : str) {
					String[] split = s.split(":");
					if(split != null && split.length==2) {
						strValue += split[0]+"   数量： "+split[1];
						strValue +="\r\n";
					}
				}
				cell5.setCellValue(strValue);
			}
			cell5.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell6 = row.createCell(5);
			cell6.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell7 = row.createCell(6);
			cell7.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell8 = row.createCell(7);
			cell8.setCellValue((info.getMaintenancePerson()==null?"":info.getMaintenancePerson()));
			cell8.setCellStyle(getCellStyle(workbook, false, true));
			
			XSSFCell cell9 = row.createCell(8);
			cell9.setCellValue((info.getComment()==null?"":info.getComment()));
			cell9.setCellStyle(getCellStyle(workbook, false, true));
		}
		log.info("------创建动设备检维修excel表成功------");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		workbook.close();
		
		return bos;
	}
	
	
	/**
	 * 获取单元格格式CellStyle
	 * @return
	 */
	private static XSSFCellStyle getCellStyle(XSSFWorkbook workbook, boolean isBold, boolean isBorder) {
		XSSFCellStyle style = workbook.createCellStyle();
		
		if(isBorder) {
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
		}
		//换行
		style.setWrapText(true);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);
		if(isBold) {
			font.setBold(true);
		}
		style.setFont(font);
		
		return style;
	}
}
