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
 * 动设备检维修信实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentMoveInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mid;
	private String positionNum;
	private String equipName;
	private String equipVersion;
	private String maintenancePerson;
	private String maintenanceReason;
	private String maintenanceContent;
	@DateTimeFormat(iso=ISO.DATE)
	@JsonFormat(pattern="yyyy-MM-dd", timezone="UTC+8")
	private Date maintenanceTime;
	private String specificationAndNumber;
	private String comment;

}
