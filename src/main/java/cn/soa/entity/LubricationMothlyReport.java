package cn.soa.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 润滑油月度使用记录表 实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LubricationMothlyReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String oid;    //润滑油主键id
	private String oname;  //润滑油名称
	private String month;  //月份
	private String ramount;  //用油量
	
}
