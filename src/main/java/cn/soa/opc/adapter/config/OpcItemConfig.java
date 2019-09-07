package cn.soa.opc.adapter.config;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jboss.jandex.Main;

public class OpcItemConfig {

	private List<String> itemList = new ArrayList<String>();
	
	public OpcItemConfig() {
		readItem();
	}

	/**
	 * 读取opcItem的配置信息
	 */
	private void readItem(){
		ResourceBundle localResource = ResourceBundle.getBundle("OpcItem");
		String[] groups = localResource.getString("group").split(",");
		for (int i = 0; i < groups.length; i++) {
			String[] items = localResource.getString(groups[i]).split(",");
			for (int j = 0; j < items.length; j++) {
				itemList.add(items[j]);
			}
		}
	}
	public List<String> getItemList() {
		return itemList;
	}

}