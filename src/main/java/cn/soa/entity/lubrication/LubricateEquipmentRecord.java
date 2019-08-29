package cn.soa.entity.lubrication;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
	//private String lid; 
	//private String pid; 
	private String oid; 
	private String excutor; 
	private String ptime; 
	private String operatestate; 
	private String operatetype; 
	private String rnote; 
	private String ramount; 
	private String rremark1; 
	private String rremark2;
//	@Override
//	public String toString() {
//		
//		return "LubricateEquipmentRecord [rid=" + rid + ", oid=" + oid + ", excutor=" + excutor + ", ptime=" + ptime
//				+ ", operatestate=" + operatestate + ", operatetype=" + operatetype + ", rnote=" + rnote + ", ramount="
//				+ ramount + ", rremark1=" + rremark1 + ", rremark2=" + rremark2 + "]"+super.toString();
//	} 
}

