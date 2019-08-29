package cn.soa.entity.lubrication;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 润滑设备实体类
 * @author Luo Guimao
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@Accessors(chain = true)
@Validated
public class LubricateEquipment implements Serializable {
	
	private String lid;
	private String lnamekey;
	private String lname;
	private String lnumber;
	private Integer lstate;
	private String ltype;
	private String lposition1;
	private String lposition2;
	private String isuser;
	private String lremark1;
	private String lremark2;
}
