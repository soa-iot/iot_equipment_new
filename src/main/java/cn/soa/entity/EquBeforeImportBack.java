package cn.soa.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquBeforeImportBack {
    private String id;

    private String equTypeId;

    private String equTypeName;

    private String operator;

    private String operateTime;

    private String backFileName;

    private String backFilePathReal;

    private String backFilePathVirtual;

    private String standby1;

    private String standby2;

    private String standby3;

    private String column1;

   
}