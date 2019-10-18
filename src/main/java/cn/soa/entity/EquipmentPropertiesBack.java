package cn.soa.entity;

public class EquipmentPropertiesBack {
    private String id;

    private String backId;

    private String equId;

    private String proNameCn;

    private String proNameEn;

    private String proValue;

    private String standby1;

    private String standby2;

    private String standby3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId == null ? null : backId.trim();
    }

    public String getEquId() {
        return equId;
    }

    public void setEquId(String equId) {
        this.equId = equId == null ? null : equId.trim();
    }

    public String getProNameCn() {
        return proNameCn;
    }

    public void setProNameCn(String proNameCn) {
        this.proNameCn = proNameCn == null ? null : proNameCn.trim();
    }

    public String getProNameEn() {
        return proNameEn;
    }

    public void setProNameEn(String proNameEn) {
        this.proNameEn = proNameEn == null ? null : proNameEn.trim();
    }

    public String getProValue() {
        return proValue;
    }

    public void setProValue(String proValue) {
        this.proValue = proValue == null ? null : proValue.trim();
    }

    public String getStandby1() {
        return standby1;
    }

    public void setStandby1(String standby1) {
        this.standby1 = standby1 == null ? null : standby1.trim();
    }

    public String getStandby2() {
        return standby2;
    }

    public void setStandby2(String standby2) {
        this.standby2 = standby2 == null ? null : standby2.trim();
    }

    public String getStandby3() {
        return standby3;
    }

    public void setStandby3(String standby3) {
        this.standby3 = standby3 == null ? null : standby3.trim();
    }
}