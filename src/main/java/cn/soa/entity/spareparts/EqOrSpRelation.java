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

   
}