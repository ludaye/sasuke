package sasuke;

public class ClassProperty {
    private String columnName;
    private String lowCamelName;
    private String upCamelName;
    private String type;
    private String fullNameType;
    private String columnType;
    private String remark;
    private Boolean isid;

    public String getUpCamelName() {
        return upCamelName;
    }

    public void setUpCamelName(String upCamelName) {
        this.upCamelName = upCamelName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullNameType() {
        return fullNameType;
    }

    public void setFullNameType(String fullNameType) {
        this.fullNameType = fullNameType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLowCamelName() {
        return lowCamelName;
    }

    public void setLowCamelName(String lowCamelName) {
        this.lowCamelName = lowCamelName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Boolean getIsid() {
        return isid;
    }

    public void setIsid(Boolean isid) {
        this.isid = isid;
    }
}
