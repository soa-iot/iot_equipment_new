package cn.soa.opc.adapter.datacallback;

import java.util.Date;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIUnsignedInteger;
import org.jinterop.dcom.core.JIUnsignedShort;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;

public abstract class AbstractDataCallback implements DataCallback {
	/**
	 * 获取指定item的值
	 * @param var
	 * @param clazz
	 * @return
	 * @throws JIException
	 */
	protected Object getValueFromOPC(JIVariant var,Object clazz) throws JIException{
		if(clazz instanceof Float){
			return var.getObjectAsFloat();			
		}else if(clazz instanceof JIUnsignedShort){
			return var.getObjectAsUnsigned().getValue();
		}else if(clazz instanceof Character){
			return Integer.valueOf(var.getObjectAsChar());
		}else if(clazz instanceof Integer){
			return var.getObjectAsInt();
		}else if(clazz instanceof JIUnsignedInteger){
			return var.getObjectAsUnsigned().getValue();
		}else if(clazz instanceof JIString){
			return var.getObjectAsString().getString();
		}else if(clazz instanceof Date){
			return var.getObjectAsDate();
		}else if(clazz instanceof String){
			return var.getObjectAsString2();
		}else if(clazz instanceof Double){
			return var.getObjectAsDouble();
		}else if(clazz instanceof Boolean){
			return var.getObjectAsBoolean();
		}else if(clazz instanceof Long){
			return var.getObjectAsLong();
		}else if(clazz instanceof Short){
			return var.getObjectAsShort();
		}
		
		return clazz;
	}
	/**通过Key获取属性
	 * @param key
	 * @return
	 */
	public abstract Object getValue(String key);
	public void changed(Item item, ItemState itemState) {
		// TODO Auto-generated method stub
		
	}
}
