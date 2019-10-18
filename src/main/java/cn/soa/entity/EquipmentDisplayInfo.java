package cn.soa.entity;

public class EquipmentDisplayInfo {
    private String id;

    private String pId;

    private String equTypeId;

    private String field;

    private String title;

    private String formName;

    private String width;

    private String displayType;

    private String fixed;

    private String sort;

    private Short colspan;

    private Short rowspan;

    private Short classNum;

    private Short propertyType;

    private Short isDisplay;

    private Short columnType;

    private Short isSearch;

    private Integer sortNum;

    private String standby1;

    private String standby2;

    private String standby3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public String getEquTypeId() {
        return equTypeId;
    }

    public void setEquTypeId(String equTypeId) {
        this.equTypeId = equTypeId == null ? null : equTypeId.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName == null ? null : formName.trim();
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width == null ? null : width.trim();
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType == null ? null : displayType.trim();
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed == null ? null : fixed.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public Short getColspan() {
        return colspan;
    }

    public void setColspan(Short colspan) {
        this.colspan = colspan;
    }

    public Short getRowspan() {
        return rowspan;
    }

    public void setRowspan(Short rowspan) {
        this.rowspan = rowspan;
    }

    public Short getClassNum() {
        return classNum;
    }

    public void setClassNum(Short classNum) {
        this.classNum = classNum;
    }

    public Short getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Short propertyType) {
        this.propertyType = propertyType;
    }

    public Short getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Short isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Short getColumnType() {
        return columnType;
    }

    public void setColumnType(Short columnType) {
        this.columnType = columnType;
    }

    public Short getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Short isSearch) {
        this.isSearch = isSearch;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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