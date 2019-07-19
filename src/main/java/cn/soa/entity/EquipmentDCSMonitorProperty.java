package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DCS点位提供监控属性 实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDCSMonitorProperty implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mpid;
	private String name;
	private String key;
	private String remark1;
	private String remark2;

}
