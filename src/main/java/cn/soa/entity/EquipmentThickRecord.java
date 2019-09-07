package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备测厚记录实体类
 * @author Hang
 * @since 2019-08-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentThickRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	private String trid;   //主键
	private String eid;    //设备id
	private String positionnum;    //测厚点位名称
	private String measurevalue;   //测厚点位值
	private String measuror;    //测厚执行人
	@JsonFormat(pattern="yyyy-MM-dd", timezone="UTC+8")
	private Date measuretime;    //测厚执行时间
	private String ttype;    //测厚类型
	private String tstate;    //测厚状态
	private String tnote;    //描述
	private String tremark1;
	private String tremark2;
	/* 添加设备位号和设备名称 */
	private String equipPositionNum;   //设备位号
	private String equipName;          //设备名称
	private String filepath;          //设备测点示图
}
