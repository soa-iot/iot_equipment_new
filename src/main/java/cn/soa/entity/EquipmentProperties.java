package cn.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquipmentProperties {
    private String id;

    private String equId;

    private String proNameCn;

    private String proNameEn;

    private String proValue;

    private String standby1;

    private String standby2;

    private String standby3;

   
}