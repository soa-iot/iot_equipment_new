package cn.soa.opc.adapter.config;

import java.util.ResourceBundle;

public final class OpcServerConfig {

	public static String host; // 访问的opc server的IP地址
	public static String domain; // 访问的opc server的域名，没有就设置为IP地址
	public static String clsid; // windows系统对于opc server分配一个唯一表示它的ID代码
	public static String user; // opc server的登录用户名
	public static String password; // opc server的登录密码
	public static String period; // 获取opc server的opcitem的时间频率，毫秒级
	public static String initialRefresh; // 是否初始时刷新
	public static String dataSaveMode; // 数据存储方式:Redis/Memory
	public static String redisServer; // redis缓存数据库IP
	public static String redisPort; // redis缓存数据库端口
	public static String redisPassword; // redis缓存数据库验证密码
	public static String reConectionTime; // OPC重连间隔时间
	public static String itemName; // OPC位号标签名
	public static String prefixName; // OPC位号前缀名
	public static String restIP; // rest服务IP
	public static String restPort; // rest服务端口号
	public static String saveDay; // 记录保存天数

	static {
		ResourceBundle localResource = ResourceBundle.getBundle("OpcServer");
		host = localResource.getString("host");
		domain = localResource.getString("domain");
		clsid = localResource.getString("clsid");
		user = localResource.getString("user");
		password = localResource.getString("password");
		period = localResource.getString("period");
		initialRefresh = localResource.getString("initialRefresh");
		dataSaveMode = localResource.getString("dataSaveMode");
		redisServer = localResource.getString("redisServer");
		redisPort = localResource.getString("redisPort");
		redisPassword = localResource.getString("redisPassword");
		itemName = localResource.getString("itemName");
		prefixName = localResource.getString("prefixName");
		restIP = localResource.getString("restIP");
		restPort = localResource.getString("restPort");
		saveDay = localResource.getString("saveDay");
	
	}
}