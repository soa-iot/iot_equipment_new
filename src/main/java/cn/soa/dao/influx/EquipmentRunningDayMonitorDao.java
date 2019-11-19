package cn.soa.dao.influx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.soa.service.impl.AddInfoToEquipementRunningS;
import cn.soa.utils.InfluxDBTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: EquipmentRunningDayMonitor
 * @Description: influxdb数据库访问层
 * @author zhugang
 * @date 2019年8月24日
 */
@Service
@Slf4j
public class EquipmentRunningDayMonitorDao {
	
	@Autowired
	private InfluxDBTemplate influxDBTemplate;
	
	/**   
	 * @Title: sumByPositionAndTime   
	 * @Description:查询统计指定时间运行时间    
	 * @return: Object        
	 */  
	public Object sumByPositionAndTime(
			String position, Date beginTime, 
			Date endTime, String number ){
		//log.info("-------------查询统计指定时间运行时间 ---------------");
		//检查
		if(StringUtils.isBlank(position) || StringUtils.isBlank(number) ) return null;
		
		//sql条件
		String condition = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if( beginTime != null) condition = " and time >='" + sdf.format(beginTime) +"'";
		if( endTime != null) {
			condition = (condition==null?"":condition + " and") + " time <='" + sdf.format(endTime) +"'";
		}
		try {
			String sql = " select sum(sum) as value from iot_equipment_running_dayMonitor "
					+ "    where position = '" + position 
					+ "'      and number = '" + number + "'"
					+ condition;
			//log.info(sql);
			QueryResult results = influxDBTemplate.query(sql);
			if( results == null) return null;
			List<Series> series = results.getResults().get(0).getSeries();
			if(series == null ) return null;
			if(series.isEmpty() ) return null;
			List<List<Object>> values = series.get(0).getValues();
			Object value = values.get(0).get(1);
			//log.info( values.toString() );
			if(value != null ) log.info( value.toString() );
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
