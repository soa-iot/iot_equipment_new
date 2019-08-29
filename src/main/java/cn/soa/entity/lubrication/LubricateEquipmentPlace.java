package cn.soa.entity.lubrication;

import java.io.Serializable;
import java.util.Date;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 润滑设备部位实体类
 * @author Luo Guimao
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@Accessors(chain = true)
@Validated
public class LubricateEquipmentPlace extends LubricateEquipment implements Serializable {
	
	private String pid;
	//private String lid;
	private String pplace;
	private String pnote;
	private String ptype;
	private String requireoil1;
	private String requireoil2;
	private String pfrequency;
	private String punit;
	private String plastamount;
	private String pamount;
	private Date nextchangetime;
	private Date lastchangetime;
	private String premark1;
	private String premark2;
}
