package cn.soa.opc.adapter.config;

import java.sql.Connection;
import java.util.ResourceBundle;

import org.json.JSONArray;

import com.soa.cloudplatform.rdb.base.AbstractRDBAccess;
import com.soa.cloudplatform.rdb.base.SoaSystemRDBFactory;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 设备预防性维护相关配置信息
 * @author  陈宇林
 * @version  [版本号, 2017年10月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EquForcastConfig {
	
	public static String saveTime;//设备预防性维护保存实时数据的时间间隔 单位秒
	
	public static String showTime;//设备预防性维护趋势图展示时间 单位 小时
	
	public static String periodTime;//超限频次统计时间间隔单位秒
	
	public static JSONArray equipInfo;//预防性维护的相关设备信息
	
	
	static {
		ResourceBundle localResource = ResourceBundle.getBundle("equForcast");
		
		saveTime = localResource.getString("saveTime");
		
		showTime = localResource.getString("showTime");
		
		periodTime = localResource.getString("peirodTime");
		
		equipInfo = loadConfig();
		
	}
	
	
	/**
	 * 从数据库获取设备的相关信息
	 */
	private static JSONArray loadConfig() {
		
		JSONArray data = null;
		AbstractRDBAccess access = null;
		Connection conn = null;//数据库连接
		try {
			
			String sql = "SELECT POSITION,EQUID,PARATYPE,EQUNAME,STANDVALUE,OPCITEM "
						+ "FROM BI_EQUIP_FORCAST_EQUIP";
			access = new SoaSystemRDBFactory().getAbstractRDBAccess();
			
			data = access.doSelectSqlAsJSON(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	
	public static void main(String[] args) {
		System.out.println(equipInfo);
	}

}
