package cn.soa.entity.lubrication;

import java.io.Serializable;
import java.util.Date;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 润滑设备换油记录实体类
 * @author Luo Guimao
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@Accessors(chain = true)
@Validated
public class LubricateEquipmentRecord extends LubricateEquipmentPlace implements Serializable {
	
	private String rid; 
//	private String lid; 
//	private String pid; 
	private String oid; 
	private String excutor; 
	private Date ptime; 
	private String operatestate; 
	private String operatetype; 
	private String rnote; 
	private Double ramount; 
	private String rremark1; 
	private String rremark2;
	
	/*添加润滑部位和润滑油品字段*/
	private String requireoil;
	private String pplace;
	
	
}
