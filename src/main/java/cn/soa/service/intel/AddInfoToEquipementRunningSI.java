package cn.soa.service.intel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @ClassName: AddInfoToEquipementRunningSI
 * @Description: 为查询设备运行时间，添加附加信息
 * @author zhugang
 * @date 2019年8月24日
 */
@Service
public interface AddInfoToEquipementRunningSI {

	/**   
	 * @Title: addRepaireInfo   
	 * @Description: 添加设备大修运行时间  
	 * @return: List<Map<String,Object>>        
	 */  
	List<Map<String,Object>> addRepaireInfo( List<Map<String, Object>> list, String time  );
	
	/**   
	 * @Title: addChangeInfo   
	 * @Description: 添加设备切换运行时间    
	 * @return: List<Map<String,Object>>        
	 */  
	List<Map<String,Object>> addChangeInfo( List<Map<String, Object>> list, String time );
	
	/**   
	 * @Title: addAllInfo   
	 * @Description:   添加设备总运行时间    
	 * @return: List<Map<String,Object>>        
	 */  
	List<Map<String,Object>> addAllInfo( List<Map<String, Object>> list, String time  );

	/**   
	 * @Title: addCurrMonthInfo   
	 * @Description:  添加设备当月运行时间    
	 * @return: List<Map<String,Object>>        
	 */  
	List<Map<String, Object>> addCurrMonthInfo(List<Map<String, Object>> list, String startTimeStr, String endTimeStr);
}
