
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

import cn.soa.entity.spareparts.SparePart;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryCondition {

	private String beginDate;// 开始时间

	private String endDate;// 结束时间

	private String id;// id

	private String equType;// 设备类型

	private String equTypeId;// 设备类型id

	private Integer page;// 页码

	private Integer limit;// 每页数据条数

	private String equId;// 设备id

	private String exportType;// 导出类型 1-空白模板，2-部分数据 3-所有数据

	private List<EquipmentCommonInfo> equipmentList;// 设备数据

	private EquipmentCommonInfo equipmentCommonInfo;// 单条设备数据

	private String operatePeople;// 操作人员

	private String operateType;// 操作类型

	private String uuid;// uuid

	private String keyWord;// 关键字

	private String backId;// 设备历史操作表id

	private String equPositionNum;// 设备位号

	@ApiModelProperty("设备备件信息（用于筛选条件）")
	private SparePart sparePart;// 设备备件信息

	@ApiModelProperty("设备备件信息是否为告警信息（用于查询设备备件告警信息时传true）")
	private String alarm;// 默认'false'

}
