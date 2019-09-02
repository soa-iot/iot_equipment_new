package cn.soa.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.soa.dao.influx.EquipmentRunningMonthMonitorDao;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class RunningEquipmentMontMonitorSchemeTask {
	@Autowired
	private EquipmentRunningMonthMonitorDao dao; 
	   //cron表达式：每月1号凌晨启动
    @Scheduled(cron = "0 0 0 1 ? ?")
    private void insertToEquipmentMonthMonitor() {
    	String startTime=getLastDayOfMonth("min");
    	String endTime=getLastDayOfMonth("max");
    	dao.insertEquipmentMonthMonitor(startTime, endTime);
    }

    //获取每个月的最后一天或者第一天
    public static String getLastDayOfMonth(String mark)
    {
    
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar cal = Calendar.getInstance();
      //设置月份
     int month= cal.get(Calendar.MONTH);
     log.info(month+"");
     cal.set(Calendar.MONTH, month-1);
      //获取某月最大天数
     int LastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
      //获取某月最小天数
     int FirstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
      //设置日历中月份的最小天数
     if(mark.equals("min")) {
    	  cal.set(Calendar.DAY_OF_MONTH, FirstDay);
     }else {
    	 cal.set(Calendar.DAY_OF_MONTH, LastDay);
     }
      String DayOfMonth = sdf.format(cal.getTime());
      return DayOfMonth+" 00:00:00";
    }
    
    public static void main(String[] args) {
    	String data=getLastDayOfMonth("min");
    	 log.info(data);
    	
	}
}
