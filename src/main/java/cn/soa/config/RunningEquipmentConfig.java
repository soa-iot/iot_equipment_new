package cn.soa.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.RunningEquipments;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import cn.soa.task.EquipmentRunningMonitor;

@Configuration
@EnableScheduling
@PropertySource("classpath:/config/equipmentRunning.properties")
public class RunningEquipmentConfig {
	private static Logger logger = LoggerFactory.getLogger( RunningEquipmentConfig.class );
		
	@Autowired
	private EquipmentMoveRunningTimeSI equipRunningS;
	
	@Autowired
	private EquipmentRunningMonitor equipmentRunningMonitor;
	
	@Value( "${equipment.running.monitor}" )
	private boolean isMonitor;
	
	/**   
	 * @Title: getRunningEquipments   
	 * @Description:  获取所有需要监控点位信息 
	 * @return: RunningEquipments        
	 */  
	@Bean
	public RunningEquipments getRunningEquipments() {
		try {
			ArrayList<String> positionNums = new ArrayList<String>();
			List<EquipmentMoveRunningTime> equipments = equipRunningS.getRunningEquipment();
			for( EquipmentMoveRunningTime e : equipments ) {
				String currDcsPositionNum = e.getDcsPositionNum();
				if( currDcsPositionNum != null ) {
					positionNums.add( currDcsPositionNum );
				}else {
					logger.info( "---------获取所有需要监控点位信息失败的设备信息：-------" );
					logger.info( e.getPositionNum() );
				}
			}
			if( positionNums == null || positionNums.size() < 1 ) return null;
			logger.info( positionNums.toString() );
			RunningEquipments runningEquipments = new RunningEquipments();
			runningEquipments.runningPositions = positionNums;
			return runningEquipments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**   
	 * @Title: startEquipmentRunningMonitor   
	 * @Description: 开启动设备运行监听  
	 * @return: void        
	 */  
	@Scheduled(cron = "0 0/10 * * * * ?")
	public void startEquipmentRunningMonitor() {
		logger.info( "--init---------开启动设备运行监听---------" );
		//判断是否开启监听
		if( isMonitor ) {
			try {
				Map<String, Object> data = equipmentRunningMonitor.startMonitorDataT();
				if( data.isEmpty() ) {
					logger.info( "--init---------失败：数据为空---------" );
				}
				logger.info( "--init---------开启动设备运行监听成功---------" );
			} catch (Exception e) {
				logger.info( "--init---------失败---------" );
				e.printStackTrace();
			}
		}else {
			logger.info( "--init---------配置关闭设备运行监听---------" );
		}		
	}
}
