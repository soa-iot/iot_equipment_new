
/**
 * <一句话功能描述>
 * <p>服务器返回数据格式
 * @author 陈宇林
 * @version [版本号, 2019年4月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("返回实体") 
public class ResponseEntity<T> {

	@ApiModelProperty("状态:0-请求成功；-1-请求失败")
	private int code;// 数据状态

	@ApiModelProperty("消息")
	private String msg;// 返回消息

	@ApiModelProperty("数据数量")
	private long count;// 数据条数

	@ApiModelProperty("数据")
	private T data;// 数据

}
