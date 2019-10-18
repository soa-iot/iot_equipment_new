package cn.soa.entity;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Case;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquipmentCommonInfo {

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

	private List<EquipmentProperties> equipmentProperties;

	public void setProperty(String propertyName, String value) {
		switch (propertyName) {
		case "equId":
			this.setEquId(value);
			break;
		case "equName":
			this.setEquName(value);
			break;
		case "equStatus":

			switch (value) {
			case "在用":
				value = "1";
				break;
			case "备用":
				value = "2";
				break;
			case "停用":
				value = "3";
				break;
			case "闲置":
				value = "4";
				break;
			case "报废":
				value = "5";
				break;
			default:
				value = "7";
				break;

			}
			this.setEquStatus(value);
			break;
		case "equPositionNum":
			this.setEquPositionNum(value);
			break;
		case "processUnits":
			this.setProcessUnits(value);
			break;
		case "equModel":
			this.setEquModel(value);
			break;
		case "equType":
			this.setEquType(value);
			break;
		case "equTypeId":
			this.setEquTypeId(value);
			break;
		case "assetValue":
			this.setAssetValue(value);
			break;
		case "equManufacturer":
			this.setEquManufacturer(value);
			break;
		case "equProducDate":
			this.setEquProducDate(value);
			break;
		case "equCommissionDate":
			this.setEquCommissionDate(value);
			break;
		case "equInstallPosition":
			this.setEquInstallPosition(value);
			break;
		case "standby1":
			this.setStandby1(value);
			break;
		case "standby2":
			this.setStandby2(value);
			break;
		case "standby3":
			this.setStandby3(value);
			break;

		default:
			break;
		}
	};

}