package influxdb.test;

import java.util.HashMap;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import cn.soa.dao.influx.EquipmentRunningMonthMonitorDao;
import cn.soa.service.impl.EquipmentMoveRunningBYyearS;

public class Test01 {
	
	@Test
	public void test() {
		EquipmentMoveRunningBYyearS dao =new  EquipmentMoveRunningBYyearS();
		dao.findChangeOrFixDate("1212", "da", "2019");
	 
	}
	

}
