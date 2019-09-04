
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2019年9月4日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {

	// 单元格内容
	private String content;
	// 字段名称，用户导出表格时反射调用
	private String fieldName;
	// 这个单元格的集合
	private List<Column> listTpamscolumn = new ArrayList<Column>();

	int totalRow;
	int totalCol;
	int row;// excel第几行
	int col;// excel第几列
	int rLen; // excel 跨多少行
	int cLen;// excel跨多少列
	private boolean HasChilren;// 是否有子节点
	
	private int tree_step;// 树的级别 从0开始
	private String id;
	private String pid;

	public Column(String content, String fieldName) {
		this.content = content;
		this.fieldName = fieldName;
	}

	public Column(String fieldName, String content, int tree_step) {
		this.tree_step = tree_step;
		this.fieldName = fieldName;
		this.content = content;
	}

}
