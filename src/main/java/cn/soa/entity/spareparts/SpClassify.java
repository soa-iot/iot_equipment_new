package cn.soa.entity.spareparts;

public class SpClassify {
    private String cId;

    private String pId;

    private String classifyValue;

    private String classifyName;

    private String remark;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public String getClassifyValue() {
        return classifyValue;
    }

    public void setClassifyValue(String classifyValue) {
        this.classifyValue = classifyValue == null ? null : classifyValue.trim();
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}