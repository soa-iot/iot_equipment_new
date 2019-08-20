package cn.soa.entity.lubrication;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 设备润滑油实体类
 * @author Luo Guimao
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@Accessors(chain = true)
@Validated
public class EquipmentLubricationOil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String oid;
	private String oname;
	private String ostock;
	private Integer ostate;
	private String ounit;
	private String odescribe;
	private String otype;
	private String manufacture;
	private String osign;
	private String oremark1;
	private String oremark2;
}
