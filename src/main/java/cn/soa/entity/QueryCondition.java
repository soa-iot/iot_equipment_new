
/**
 * <一句话功能描述>
 * <p>请求条件实体类
 * @author 陈宇林
 * @version [版本号, 2019年8月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryCondition {

	private String equType;// 设备类型

	private String equTypeId;// 设备类型id

	private Integer page;// 页码

	private Integer limit;// 每页数据条数

	private String equId;// 设备id

	private String exportType;// 导出类型 1-空白模板，2-部分数据 3-所有数据
	
	private List<EquipmentCommonInfo> equipmentList;//设备数据

}
