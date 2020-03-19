package cn.soa.entity.spareparts;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpRegister {
    private String id;

    private String registerCode;

    private String registerType;

    private String explain;

    private String requestCode;

    private String registerPeople;

    private String registerDate;
    
    private String renark;

  
}