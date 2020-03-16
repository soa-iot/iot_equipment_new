package cn.soa.entity.spareparts;

import java.util.Date;

public class SpRegister {
    private String id;

    private String registerCode;

    private String registerType;

    private String explain;

    private String requestCode;

    private String registerPeople;

    private Date registerDate;

    private String renark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode == null ? null : registerCode.trim();
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType == null ? null : registerType.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode == null ? null : requestCode.trim();
    }

    public String getRegisterPeople() {
        return registerPeople;
    }

    public void setRegisterPeople(String registerPeople) {
        this.registerPeople = registerPeople == null ? null : registerPeople.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRenark() {
        return renark;
    }

    public void setRenark(String renark) {
        this.renark = renark == null ? null : renark.trim();
    }
}