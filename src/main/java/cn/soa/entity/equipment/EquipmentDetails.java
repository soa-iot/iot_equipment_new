package cn.soa.entity.equipment;

import java.io.Serializable;
import java.util.Date;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 设备详情实体类
 * @author Bru.Lo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@Accessors(chain = true)
@Validated
public class EquipmentDetails implements Serializable{

	private String rn;//设备名称
	private String equ_name;//设备名称
	private String equ_position_num;//设备位号
	private String equ_model;//规格型号
	private String 	supervisoryperson;//检维修人员
	private String 	impacts;//检维修原因
	private String action;//检维修内容
	private Date supervisorydate;//检维修时间
	private String process_desc;//更换备品备件型号规格和数量
	private String others;//备注
	                  
}
