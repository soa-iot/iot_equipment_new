package cn.soa.entity.spareparts;

import java.util.Date;

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

    private Date productionDate;

    private String remark;

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId == null ? null : spId.trim();
    }

    public String getSpEncoding() {
        return spEncoding;
    }

    public void setSpEncoding(String spEncoding) {
        this.spEncoding = spEncoding == null ? null : spEncoding.trim();
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Short getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Short unitCost) {
        this.unitCost = unitCost;
    }

    public Short getSpInventory() {
        return spInventory;
    }

    public void setSpInventory(Short spInventory) {
        this.spInventory = spInventory;
    }

    public Short getPrewarningVal() {
        return prewarningVal;
    }

    public void setPrewarningVal(Short prewarningVal) {
        this.prewarningVal = prewarningVal;
    }

    public String getProcurementCycle() {
        return procurementCycle;
    }

    public void setProcurementCycle(String procurementCycle) {
        this.procurementCycle = procurementCycle == null ? null : procurementCycle.trim();
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode == null ? null : labelCode.trim();
    }

    public String getManufactureFactory() {
        return manufactureFactory;
    }

    public void setManufactureFactory(String manufactureFactory) {
        this.manufactureFactory = manufactureFactory == null ? null : manufactureFactory.trim();
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}