package cn.soa.entity.spareparts;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EqOrSpRelation {

	private String rId;

	private String eqFieid;

	private BigDecimal type;

	private String costomValue;

	private String remark;

	private String remark1;

	private String eqId;

	/**
	 * 动态设置参数
	 * @param propertyName
	 * @param value
	 */
	public void setProperty(String propertyName, String value) {
		switch (propertyName) {
		case "rId":
			this.setRId(value);
			break;
		case "eqFieid":
			this.setEqFieid(value);
			break;
		case "type":
			this.setType(BigDecimal.valueOf(Double.valueOf(value)));
			break;
		case "costomValue":
			this.setCostomValue(value);
			break;
		case "remark":
			this.setRemark(value);
			break;
		case "remark1":
			this.setRemark1(value);
			break;
		case "eqId":
			this.setEqId(value);
			break;

		default:
			break;
		}
	}

}