package cn.soa.entity;

public class EquipmentSpare {
    private String spareId;

    private String sparePartsType;

    private String materialCoding;

    private String materialName;

    private String materialSpecifications;

    private String unit;

    private String actualInventory;

    private String reasonableInventory;

    private String remarkone;

    private String remarktwo;

    private String remarkthree;

    private String remarkfour;

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId == null ? null : spareId.trim();
    }

    public String getSparePartsType() {
        return sparePartsType;
    }

    public void setSparePartsType(String sparePartsType) {
        this.sparePartsType = sparePartsType == null ? null : sparePartsType.trim();
    }

    public String getMaterialCoding() {
        return materialCoding;
    }

    public void setMaterialCoding(String materialCoding) {
        this.materialCoding = materialCoding == null ? null : materialCoding.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getMaterialSpecifications() {
        return materialSpecifications;
    }

    public void setMaterialSpecifications(String materialSpecifications) {
        this.materialSpecifications = materialSpecifications == null ? null : materialSpecifications.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getActualInventory() {
        return actualInventory;
    }

    public void setActualInventory(String actualInventory) {
        this.actualInventory = actualInventory == null ? null : actualInventory.trim();
    }

    public String getReasonableInventory() {
        return reasonableInventory;
    }

    public void setReasonableInventory(String reasonableInventory) {
        this.reasonableInventory = reasonableInventory == null ? null : reasonableInventory.trim();
    }

    public String getRemarkone() {
        return remarkone;
    }

    public void setRemarkone(String remarkone) {
        this.remarkone = remarkone == null ? null : remarkone.trim();
    }

    public String getRemarktwo() {
        return remarktwo;
    }

    public void setRemarktwo(String remarktwo) {
        this.remarktwo = remarktwo == null ? null : remarktwo.trim();
    }

    public String getRemarkthree() {
        return remarkthree;
    }

    public void setRemarkthree(String remarkthree) {
        this.remarkthree = remarkthree == null ? null : remarkthree.trim();
    }

    public String getRemarkfour() {
        return remarkfour;
    }

    public void setRemarkfour(String remarkfour) {
        this.remarkfour = remarkfour == null ? null : remarkfour.trim();
    }
}