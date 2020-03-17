
/**
 * <一句话功能描述>
 * <p>备件出入库实体类
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
@ApiModel("备件出入库实体--SparepartOutInEntity")
public class SparepartOutInEntity {

	@ApiModelProperty("备件出入库操作类型：out-出库；in-采购入库；normalIn-普通入库")
	private String operateType;// 操作类型

	@ApiModelProperty("备件出入库登记实体")
	private SpRegister spRegister;// 备件出入库登记实体

	@ApiModelProperty("备件出入库记录实体数组")
	private List<SpRecord> spRecords;// 备件出入库记录实体

}
