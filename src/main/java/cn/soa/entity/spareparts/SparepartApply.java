
/**
 * <一句话功能描述>
 * <p> 备件申请单、备件出入库记录实体类
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity.spareparts;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("备件申请单、备件出入库记录实体类--SparepartApply")
public class SparepartApply {
	
	@ApiModelProperty("备件申请单")
	private SpPutIn spPutIn;//备件申请单
	
	@ApiModelProperty("备件出入库记录数组")
	private List<SpRecord> spRecords;//备件出入库记录数组

}
