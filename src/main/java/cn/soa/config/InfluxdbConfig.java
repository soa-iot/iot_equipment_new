package cn.soa.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import cn.soa.utils.InfluxDBTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
@SuppressWarnings( "serial" )
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors( chain=true )
public class InfluxdbConfig {
	
	
	private static Logger logger = LoggerFactory.getLogger( InfluxdbConfig.class );
	
	//用户名
	@Value("${spring.influx.user}")
	private String username;
	
	// 密码
	@Value("${spring.influx.password}")
	private String password;
	
	// 连接地址
	@Value("${spring.influx.url}")
	private String openurl;
	
	// 数据库
	@Value("${spring.influx.database}")
	private String database;
	
	// 保留策略
	@Value("${spring.influx.retentionPolicy}")
	private String retentionPolicy;
	
	@Bean  
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {  
        return new PropertySourcesPlaceholderConfigurer();  
    }  
	
	@Bean(name="influxDB")
	public InfluxDB influxDbBuild() {
			InfluxDB influxDB = InfluxDBFactory.connect( 
					openurl, username, password );
		try {
			if (!influxDB.databaseExists( database )) {
				logger.info( "-----指定数据库不存在-----创建---");
				influxDB.createDatabase( database );
				logger.info( "-----指定数据库创建成功-------");
			}
		} catch (Exception e) {
			// 该数据库可能设置动态代理，不支持创建数据库
			e.printStackTrace();
			logger.info( "-----指定数据库创建失败-------");
			return null;
		} finally {
			retentionPolicy = retentionPolicy == null || retentionPolicy.equals("") ? "autogen" : retentionPolicy;			
			influxDB.setRetentionPolicy( retentionPolicy );
		}
		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
		logger.info( "-----指定数据库连接建立成功-------" + influxDB );
		return influxDB;
	}
}
