package cn.soa.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.URIEditor;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义工具类
 * @author Jiang, Hang
 *
 */
@Slf4j
public class CommonUtil {
	
	/**
	 * 日期格式化成指定格式的字符串
	 * @param date 日期参数
	 * @return 格式化后的字符串
	 */
	public static String dateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
		return format.format(date);
	}
	
	/**
	 * 根据时间范围“最近三天，最近一周，最近一个月”，推算出具体日期
	 * @param timeRange 时间范围“最近三天，最近一周，最近一个月”
	 * @return 生成的日期字符串
	 */
	public static String timeRangeToDateString(String timeRange) {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if("最近三天".equals(timeRange)) {
			cal.add(Calendar.DAY_OF_MONTH, 3);
		}else if("最近一周".equals(timeRange)) {
			cal.add(Calendar.DAY_OF_MONTH, 7);
		}else if("最近一个月".equals(timeRange)) {
			cal.add(Calendar.DAY_OF_MONTH, 30);
		}else {
			return null;
		}
		log.info("推算出的日期为：{}", dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}
	
	/**
	 * 生成保存上传的文件的文件对象
	 * @param rootDir 根目录路径
	 * @param parentFolder 父目录名
	 * @param filename 文件名
	 * @return 文件路径
	 * @throws IOException 
	 */
	public static File saveFile(String rootDir, String parentFolder, String filename) throws IOException {
		
		//1.判断根目录路径是否存在
		URI uri = URI.create(rootDir);
		File root = new File(uri);
		log.info("------根目录路径 rootDir={}", root.toURI());
		if(!root.exists()) {
			root.mkdirs();
		}
		
		//2.判断父目录是否存在
		File parent = null;
		if(parentFolder != null) {
			parent = new File(root, parentFolder);
			if(!parent.exists()) {
				parent.mkdir();
			}
		}else {
			parent = root;
		}
		
		//3. 创建文件
		File file = new File(parent, filename);
		file.createNewFile();
		return file;
	}
}

