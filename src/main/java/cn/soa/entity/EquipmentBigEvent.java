package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备大事件(大修、更换)	实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentBigEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private String beid;
	private String mrid;
	private String positionNum;
	private String event;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date eventTime;
	private String remark1;
	private String remark2;

}
