package cn.soa.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquipmentType {
	private String id;

	private String title;//设备分类名称

	private String typeParentId;

	private String standby1;

	private String standby2;

	private String standby3;

	private List<EquipmentType> children;

}