package cn.soa.entity.lubrication;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备润滑油入库记录实体类
 * @author Luo Guimao
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentOilRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String orid;
	private String oid;
	private Date rtime;
	private String rid;
	private String rinout;
	private Double ramount;
	private String userid;
	private String rtype;
	private String rnote;
	private String rremark1;
	private String rremark2;
	private Double rstock;

}
