package service.test;

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
import cn.soa.service.impl.AddInfoToEquipementRunningS;
import cn.soa.service.impl.EquipmentMoveRunningTimeS;
import cn.soa.utils.InfluxDBTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class AddInfoToEquipementRunningSTest {
	
	@Autowired
	private AddInfoToEquipementRunningS addInfoToEquipementRunningS;
	
	
	@Test
	public void addRepaireInfo() {
		String time = "2019-08-31 00:00:00";

		List<Map<String, Object>> test = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("position", "P-1301(A)");
		m.put("number", "1");
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("equipment", "P-1302-B");
		m1.put("number", "1");
		
		test.add(m);
		test.add(m1);
		addInfoToEquipementRunningS.addRepaireInfo(test, time);
	}

}
