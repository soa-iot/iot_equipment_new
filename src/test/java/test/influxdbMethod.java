package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.soa.IotEquipmentApplication;
import cn.soa.service.impl.EquipmentMoveRunningTimeS;
import cn.soa.utils.InfluxDBTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class influxdbMethod {
	
	@Autowired
	private InfluxDBTemplate influxDBTemplate;
	
	@Autowired
	private EquipmentMoveRunningTimeS equipmentMoveRunningTimeS;
	
//	@Test
	public void connectInfluxdb() {
		System.out.println(influxDBTemplate);
//		influxDBTemplate.createDB( "success" );
//		String measurement = "son";
//		Map<String,String> tags = new HashMap<String,String>();
//		tags.put("tag1", "1");
//		tags.put("tag2", "2");
//		Map<String,Object> fields = new HashMap<String,Object>();
//		fields.put("field1", "1");
//		fields.put("field2", "2");
//		influxDBTemplate.insert(measurement, tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
	
	/**   
	 * @Title: generateImitateData   
	 * @Description: 模拟数据  
	 * @return: void        
	 */  
	@Test
	public void generateImitateData() {
		String[] positionArray = {"131-PI-101", "132-PI-101", "133-PI-101", "134-PI-101"};
		String measurement = "iot_equipment_running_monitor";
		List<Object> positions = Arrays.asList( positionArray );
		for( Object o : positions ) {
			Map<String,String> tags = new HashMap<String,String>();
			tags.put("position", o.toString());
			tags.put("number", "1");
			Map<String,Object> fields = new HashMap<String,Object>();
			fields.put("value", "10");
			influxDBTemplate.insert( measurement, tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		
		
	}

}
