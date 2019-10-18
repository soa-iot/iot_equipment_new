package cn.soa.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquipmentCommonInfoBack {
	private String backId;

	private String operateType;

	private String operateTime;

	private String operatePeople;

	private String equId;

	private String equName;

	private String equStatus;

	private String equPositionNum;

	private String processUnits;

	private String equModel;

	private String equType;

	private String equTypeId;

	private String assetValue;

	private String equManufacturer;

	private String equProducDate;

	private String equCommissionDate;

	private String equInstallPosition;

	private String standby1;

	private String standby2;

	private String standby3;

	private List<EquipmentPropertiesBack> equipmentPropertiesBack;
}