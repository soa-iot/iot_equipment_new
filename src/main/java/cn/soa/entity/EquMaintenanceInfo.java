
/**
 * <一句话功能描述>
 * <p>设备检维修信息实体类
 * @author 陈宇林
 * @version [版本号, 2019年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquMaintenanceInfo {
	
	private String tProblemRepId;//问题表id
	
	private String equId;//设备id
	
	private String equName;//设备名称
	
	private String equPositionNum;//设备位号
	
	private String applyPeople;//上报人
	
	private String applyDate;//上报时间
	
	private String problemState;//问题状态
	
	private String problemDesc;//问题描述
	
}
