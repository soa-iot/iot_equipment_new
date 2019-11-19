package cn.soa.opc.adapter.datacallback;

import org.apache.logging.log4j.Logger;

import com.soa.cloudplatform.dataaccess.common.soalog.SOALOG;


public final class DataCallbackFactory {
	private static Logger logger = new SOALOG(DataCallbackFactory.class)
			.getLogger();

	public static AbstractDataCallback newInstance(String type) {
		AbstractDataCallback dataCallback = null;
		DataCallbackMode mode;
		String className = "";
		try {
			mode = DataCallbackMode.valueOf(type);
		} catch (Exception e) {
			// 若存储方式不存在默认为在内存中存取数据
			mode = DataCallbackMode.memory;
		}
		switch (mode) {
		case redis:
			className = "cn.soa.opc.adapter.datacallback.RedisDataCallback";
			break;
		case memory:
		default:
			className = "cn.soa.opc.adapter.datacallback.MemoryDataCallback";
			break;
		}
		Class clazz;
		try {
			clazz = Class.forName(className);
			dataCallback = (AbstractDataCallback) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return dataCallback;
	}
}
