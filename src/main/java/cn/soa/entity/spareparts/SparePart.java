package cn.soa.entity.spareparts;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SparePart {
	private String spId;

	private String spEncoding;

	private String spName;

	private String brand;

	private String type;

	private String specification;

	private String unit;

	private Short unitCost;

	private Short spInventory;

	private Short prewarningVal;

	private String procurementCycle;

	private String labelCode;

	private String manufactureFactory;

	private String productionDate;

	private String remark;

	/**
	 * @param key
	 * @param valueOf
	 */
	public void setProperty(String key, String value) {

		switch (key) {
		case "SP_ID":
		case "spId":
			this.setSpId(value);
			break;
		case "SP_ENCODING":
		case "spEncoding":
			this.setSpEncoding(value);

			break;
		case "SP_NAME":
		case "spName":
			this.setSpName(value);
			break;
		case "BRAND":
		case "brand":
			this.setBrand(value);
			break;
		case "TYPE":
		case "type":
			this.setType(value);
			break;
		case "SPECIFICATION":
		case "specification":
			this.setSpecification(value);
			break;
		case "UNIT":
		case "unit":
			this.setUnit(value);
			break;
		case "UNIT_COST":
		case "unitCost":
			this.setUnitCost(Short.valueOf(value.substring(0,value.indexOf("."))));
			break;
		case "SP_INVENTORY":
		case "spInventory":
			this.setSpInventory(Short.valueOf(value.substring(0,value.indexOf("."))));
			break;
		case "PREWARNING_VAL":
		case "prewarningVal":
			this.setPrewarningVal(Short.valueOf(value.substring(0,value.indexOf("."))));
			break;
		case "PROCUREMENT_CYCLE":
		case "procurementCycle":
			this.setProcurementCycle(value);
			break;
		case "LABEL_CODE":
		case "labelCode":
			this.setLabelCode(value);
			break;
		case "MANUFACTURE_FACTORY":
		case "manufactureFactory":
			this.setManufactureFactory(value);
			break;
		case "PRODUCTION_DATE":
		case "productionDate":
			this.setProductionDate(value);
			break;
		case "REMARK":
		case "remark":
			this.setRemark(value);
			break;
		default:
			break;
		}

	}
}