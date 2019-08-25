package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private Integer pointnum;   //测点数量
	private String cycle;     //测量周期
	private String cycleunit;    //周期单位
	@DateTimeFormat(iso=ISO.DATE)
	@JsonFormat(pattern="yyyy-MM-dd", timezone="UTC+8")
	private Date nextmeasuretime;   //下一次测厚时间
	private String filepath;    //图片路径
	private String creator;     //创建人
	private Date creatime;      //创建时间
	private String tremark1;
	private String tremark2;
	/* 添加设备台账中的字段 */
	private String equCommissionDate; //投运日期
	private String equProducDate;    //生产日期
	private String material;     //设备材质
	private String equModel;     //规格型号
	private String equMemoOne;   //设备类别
	private String meduimType;   //工作介质
	private String welName;     //装置列名
}
