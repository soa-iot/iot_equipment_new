package cn.soa.opc.adapter.datacallback;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;

import com.soa.cloudplatform.dataaccess.common.soalog.SOALOG;


public final class MemoryDataCallback extends AbstractDataCallback {
	private Logger logger = new SOALOG(MemoryDataCallback.class).getLogger();
	private Map<String, String> cacheMap = new HashMap<String, String>(); // 用来缓存数据的Map

	@Override
	public void changed(Item item, ItemState itemState) {
		try {
			Object value = getValueFromOPC(itemState.getValue(), itemState
					.getValue().getObject());
			//System.out.println(value);
			cacheMap.put(item.getId(), value + "");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Object getValue(String key) {
		return cacheMap.get(key);
	}

}
