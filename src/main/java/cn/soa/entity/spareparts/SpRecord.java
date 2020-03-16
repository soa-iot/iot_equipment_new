package cn.soa.entity.spareparts;

public class SpRecord {
    private String id;

    private String requestCode;

    private String typr;

    private String spId;

    private String spName;

    private Short spInventory;

    private Short quantity;

    private Short unitCost;

    private String unit;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode == null ? null : requestCode.trim();
    }

    public String getTypr() {
        return typr;
    }

    public void setTypr(String typr) {
        this.typr = typr == null ? null : typr.trim();
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId == null ? null : spId.trim();
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public Short getSpInventory() {
        return spInventory;
    }

    public void setSpInventory(Short spInventory) {
        this.spInventory = spInventory;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Short getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Short unitCost) {
        this.unitCost = unitCost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}