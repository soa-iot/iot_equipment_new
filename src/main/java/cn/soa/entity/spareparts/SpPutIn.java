package cn.soa.entity.spareparts;

import java.util.Date;

public class SpPutIn {
    private String id;

    private String requestCode;

    private String proposer;

    private Date applicationDate;

    private String type;

    private String applicationStatus;

    private Date passDate;

    private String outPutStatus;

    private Date outPutTime;

    private String remark;

    private String purchaseTitle;

    private String purchaseReason;

    private String standby1;

    private String standby2;

    private String standby3;

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

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus == null ? null : applicationStatus.trim();
    }

    public Date getPassDate() {
        return passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    public String getOutPutStatus() {
        return outPutStatus;
    }

    public void setOutPutStatus(String outPutStatus) {
        this.outPutStatus = outPutStatus == null ? null : outPutStatus.trim();
    }

    public Date getOutPutTime() {
        return outPutTime;
    }

    public void setOutPutTime(Date outPutTime) {
        this.outPutTime = outPutTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPurchaseTitle() {
        return purchaseTitle;
    }

    public void setPurchaseTitle(String purchaseTitle) {
        this.purchaseTitle = purchaseTitle == null ? null : purchaseTitle.trim();
    }

    public String getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason == null ? null : purchaseReason.trim();
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