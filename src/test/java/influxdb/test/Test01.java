package influxdb.test;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.Test;

public class Test01 {
	
	@Test
	public void createDB() {
		InfluxDB db = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
		//创建数据库javadb
		Query query = new Query("create database javadb", "javadb");
		QueryResult result = db.query(query);
		System.out.println(result);
	}
}
