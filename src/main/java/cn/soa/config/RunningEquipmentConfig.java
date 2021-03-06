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

import cn.soa.domain.EquipmentRunningInitInfo;
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
		logger.info( "--init---------isMonitor---------" + isMonitor);
		if( !isMonitor ) return null;
		logger.info( "--init---------获取所有需要监控点位信息 ---------" );
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
			logger.info( "---------获取所有需要监控点位信息：-------" );
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
	@Scheduled(cron = "0 0/10 * * * ?")
	public void startEquipmentRunningMonitor() {
		logger.info( "----------开启动设备运行监听---------" );
		//判断是否开启监听
		if( isMonitor ) {
			try {
				Map<String, Integer> data = equipmentRunningMonitor.startMonitorDataT();
				if( data == null || data.isEmpty() ) {
					logger.info( "----------失败：数据为null或空---------" );
					logger.info( "----------设备运行时间开启失败……" );
					return ;
				}
				logger.info( "----------开启动设备运行监听成功---------" );
			} catch (Exception e) {
				logger.info( "-----------未知错误，设备运行时间开启失败……---------" );
				e.printStackTrace();
			}
		}else {
			logger.info( "-----------配置关闭设备运行监听---------" );
		}		
	}
	
	@Bean
	public EquipmentRunningInitInfo init(){
		logger.info("----------初始化加载动设备运行时间的设备信息---------");
		try {
			EquipmentRunningInitInfo e = new EquipmentRunningInitInfo();
			e.setEquipmentInfoKeyPosition( equipRunningS.getEquipPositionAndNumberAndDCS() );
			logger.info("----------初始化加载动设备运行时间成功---------");
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("----------初始化加载动设备运行时间失败---------");
			return null;
		}
		
	}
}
