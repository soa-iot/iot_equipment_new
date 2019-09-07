package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备润滑油加油和换油记录 实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LubricationRecordReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String lid;    //润滑设备主键id
	private String pid;    //润滑部位主键id
	private String oid;    //润滑油主键id
	private String oname;  //润滑油名称
	private String osign;  //牌号
	private String manufacture;  //生产厂家
	private String pplace;  //润滑部位
	private String positionnum;  //润滑设备位号
	private String tname;  //润滑设备名称
	@JsonFormat(pattern="yyyy-MM-dd", timezone="UTC+8")
	private Date ptime;    //加换油时间
	private String excutor;  //加换油执行人
	private String operatetype;  //加换油类型
	private String ramount;  //加换油量
	private String rnote;   //备注/描述信息
	
}
