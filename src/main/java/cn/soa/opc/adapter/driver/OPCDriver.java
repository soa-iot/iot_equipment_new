package cn.soa.opc.adapter.driver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jinterop.dcom.common.JISystem;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.Async20Access;
import org.openscada.opc.lib.da.AutoReconnectController;
import org.openscada.opc.lib.da.Server;

import com.soa.cloudplatform.dataaccess.common.soalog.SOALOG;
import cn.soa.opc.adapter.config.OpcServerConfig;
import cn.soa.opc.adapter.datacallback.AbstractDataCallback;
import cn.soa.opc.adapter.datacallback.DataCallbackFactory;

public final class OPCDriver {
	private static org.apache.logging.log4j.Logger logger = new SOALOG(
			OPCDriver.class).getLogger(); // 日志类
	private static AbstractDataCallback dataCallback; // OPC数据缓存类
	private static AccessBase accessBase; // OPC连接
	private static Long reConectionTime = 100000l;
	private static OPCDriver instance = null;
	private static boolean state = false;

	private OPCDriver(List<String> itemList) {
		init(itemList);
		start();
	}

	/**
	 * 获取一个OPC驱动实例,获取成功后,会自动连接配置文件中的OPCServer服务器.该驱动为单例
	 * 
	 * @param itemList
	 *            点位集合
	 * @return
	 */
	public static OPCDriver getInstance(List<String> itemList) {
		if (instance == null) {
			synchronized (OPCDriver.class) {
				if (instance == null) {
					instance = new OPCDriver(itemList);
					try {
						reConectionTime = Long
								.parseLong(OpcServerConfig.reConectionTime);
					} catch (Exception e) {
						reConectionTime = 100000l;
					}

				}
			}
		}
		return instance;
	}

	/**
	 * 向正在运行的OPC连接其中增加点位
	 * 
	 * @param item
	 */
	public static boolean addItem(String item) {
		boolean flag = false;
		try {
			accessBase.addItem(item, dataCallback);
			flag = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 从正在运行的OPC连接中移除指定点位
	 * 
	 * @param item
	 * @return
	 */
	public static boolean removeItem(String item) {
		boolean flag = false;
		try {
			accessBase.removeItem(item);
			flag = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public static AbstractDataCallback getDataCallback() {
		return dataCallback;
	}

	/**
	 * 初始化OPC连接信息
	 * 
	 * @return
	 */
	private ConnectionInformation getConnectionInformation() {
		ConnectionInformation ci = new ConnectionInformation();
		ci.setHost(OpcServerConfig.host);
		ci.setDomain(OpcServerConfig.domain);
		ci.setClsid(OpcServerConfig.clsid);
		ci.setUser(OpcServerConfig.user);
		ci.setPassword(OpcServerConfig.password);
		//ci.setProgId("KEPware.KEPServerEx.V4");
		return ci;
	}

	/**
	 * 初始化启动参数
	 * 
	 * @param config
	 *            opc配置
	 * @param itemList
	 *            点位
	 */
	private void init(List<String> itemList) {
		AutoReconnectController autos = null;
		try {
			JISystem.setAutoRegisteration(true);
			ConnectionInformation ci = getConnectionInformation();
			final Server s = new Server(ci,
					Executors.newSingleThreadScheduledExecutor());
			autos = new AutoReconnectController(s);
			autos.connect();
			Thread.sleep(100);
			accessBase = new Async20Access(s,
					Integer.parseInt(OpcServerConfig.period),
					Boolean.parseBoolean(OpcServerConfig.initialRefresh));
			String mode = OpcServerConfig.dataSaveMode;
			dataCallback = DataCallbackFactory.newInstance(mode);
			for (int i = 0, size = itemList.size(); i < size; i++) {
				accessBase.addItem(itemList.get(i), dataCallback);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始OPC连接
	 */
	private void start() {
		// 定时释放绑定后再重新绑定，因为如果长时间绑定，将无法获取数据
		Timer timer = new Timer();
		Logger log = Logger.getLogger("org.jinterop");
		log.setLevel(Level.WARNING);
		System.out.println("OPC服务等待连接中!");
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					accessBase.bind();
					if (accessBase.isActive()) {
						System.out.println("OPC服务成功连接中……");
						state = true;
					} else {
						System.out.println("OPC服务连接失败……");
					}
					Thread.sleep(reConectionTime);
					accessBase.unbind();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, Calendar.getInstance().getTime(), reConectionTime);
	}
	public static boolean isConnectionActive(){
		return state;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		List<String> itemList=new ArrayList<String>();
		OPCDriver opc = new OPCDriver(itemList);
		Thread.sleep(60000);
		Object obj=OPCDriver.getDataCallback().getValue("Line_I.HART.111-LT-101.PV");
		//Object obj=OPCDriver.getDataCallback().getValue("IoT.DCS.125-LI-104");
		System.out.println(obj);
		System.out.println(obj.toString());
	}
}
