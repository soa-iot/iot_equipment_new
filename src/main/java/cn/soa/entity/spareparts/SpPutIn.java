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
}