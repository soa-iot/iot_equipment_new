package influxdb.test;

import java.util.HashMap;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;

public class Test01 {
	
//	@Test
	public void createDB() {
		InfluxDB db = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
		//创建数据库javadb
		Query query = new Query("create database javadb", "javadb");
		QueryResult result = db.query(query);
		
		System.out.println(result);
	
	}
	
//	@Test
	public void test() {
		int a = 11 << 2;
		System.out.println(a);

	}
}
