package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备测厚管理实体类
 * @author Hang
 * @since 2019-08-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentThickManagement implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tid;    //主键
	private String eid;    //设备id
	private String positionnum;  //设备位号
	private String tname;    //设备名称
	private String measuretype;   //测量仪器型号
	private String cycle;     //测量周期
	private String cycleunit;    //周期单位
	private Date nextmeasuretime;   //下一次测厚时间
	private String creator;     //创建人
	private Date creatime;      //创建时间
	private String tremark1;
	private String tremark2;
	
}
