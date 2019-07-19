package cn.soa.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动设备(运行时间)管理表	实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentMoveRunningTime implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mrid;
	private String positionNum;
	private String name;
	private String equipType;
	private String dcsPositionNum;
	private String remark1;
	private String remark2;

}
