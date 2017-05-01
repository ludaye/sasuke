package sasuke;

public class ClassProperty {
    private String name;
    private String lowCamelName;
    private String upCamelName;
    private String type;
    private String fullNameType;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
