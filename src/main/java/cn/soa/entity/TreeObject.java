
/**
 * <一句话功能描述>
 * <p> layui树形组件数据结构
 * @author 陈宇林
 * @version [版本号, 2020年3月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity;

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
@ApiModel
public class TreeObject {
	
	@ApiModelProperty("节点标题")
	private String title;//节点标题
	
	@ApiModelProperty("节点id，设备备件分类表id")
	private String id;//节点唯一索引值，用于对指定节点进行各类操作
	
	private String field;//字段名
	
	@ApiModelProperty("子节点")
    private List<TreeObject> children;//子节点
    
    private String href;//点击节点弹出新窗口对应的 url。需开启 isJump 参数
    
    private Boolean spread;//节点是否初始展开，默认 false
    
    private Boolean checked;//节点是否初始为选中状态（如果开启复选框的话），默认 false
    
    private Boolean disabled;//节点是否为禁用状态。默认 false

}
