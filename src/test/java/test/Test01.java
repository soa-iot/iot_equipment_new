package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import cn.soa.IotEquipmentApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
public class Test01 {
	
	@Test
	public void excelTest() throws InvalidFormatException, IOException {
		ClassPathResource resource = new ClassPathResource("/config/润滑油月度记录表.xlsx");
		//生成工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(resource.getFile());
		//生成sheet表
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFCell cell = sheet.getRow(0).getCell(0);
		cell.setCellValue("123");
		System.err.println(cell );
		
		System.err.println(sheet.getRow(0).getLastCellNum());
		
		workbook.close();
	}
}
