package cn.soa.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.soa.cloudplatform.DataObjectAccess.util.SystemDataUtil;

import cn.soa.opc.adapter.driver.OPCDriver;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class OpcConfig{
		
	@Bean
	public Object initOpcClient() {
		try {
			System.out.println(">>>>>>>>>>加载OPC……");
			List<String> itemList = new ArrayList<String>();
			itemList.add("Energy.DCS.127-PI-105");
			OPCDriver.getInstance(itemList);

			//循环判断opc连接状态，连接成功则开始后面的任务
			while(true){
				if(OPCDriver.isConnectionActive()){
					System.out.println("opc成功连接……");
					break;
				}
				try {
					System.out.println("opc连接中……");
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//循环判断opc客户端获取数据
			while(true){		
				System.out.println("opc获取数据中……");
				try {
					if(OPCDriver.getDataCallback().getValue("Energy.DCS.127-PI-105") !=null){	
						log.info("opc获取数据成功……");
						log.info("点位值："+ OPCDriver.getDataCallback().getValue("Energy.DCS.127-PI-105"));
						break;
					}
					System.out.println("opc获取数据中……");
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	 
			
			//启动opc
			System.out.println("opc客户端状态正常启动");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}
}
