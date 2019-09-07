package cn.soa.opc.adapter.datacallback;

import org.apache.logging.log4j.Logger;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;

import com.soa.cloudplatform.dataaccess.common.soalog.SOALOG;
import cn.soa.opc.adapter.config.OpcServerConfig;

import redis.clients.jedis.Jedis;

public final class RedisDataCallback extends AbstractDataCallback{
	private Logger logger = new SOALOG(RedisDataCallback.class).getLogger();
	private static Jedis jedis; //Redis 缓存数据库
	static{
		String ip = OpcServerConfig.redisServer;
		String password = OpcServerConfig.redisPassword;
		int port = 6379;
		try {
			port = Integer.parseInt(OpcServerConfig.redisPort);
		} catch (Exception e) {
			port = 6379;
		}
		jedis = new Jedis(ip, port);
		jedis.auth(password);
	}
	@Override
	public void changed(Item item, ItemState itemState) {
		try {
			Object value = getValueFromOPC(itemState.getValue(),itemState.getValue().getObject());
			jedis.set(item.getId(), value+"");
		} catch (Exception e) {
			logger.error(e.getMessage());
			jedis.set(item.getId(), "");
		}
	}

	@Override
	public Object getValue(String key) {
		return jedis.get(key);
	}
}
