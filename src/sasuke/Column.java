package sasuke;

public class Column {
    private Boolean isid;
    private String name;
    private String lowCamelName;
    private String upCamelName;
    private String type;
    private String remark;
    private Integer length;

    public Column() {
    }

    public Column(Boolean isid, String name, String lowCamelName, String upCamelName, String type, String remark,
                  Integer length) {
        this.isid = isid;
        this.name = name;
        this.lowCamelName = lowCamelName;
        this.upCamelName = upCamelName;
        this.type = type;
        this.remark = remark;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUpCamelName() {
        return upCamelName;
    }

    public void setUpCamelName(String upCamelName) {
        this.upCamelName = upCamelName;
    }

    public Boolean getIsid() {
        return isid;
    }

    public void setIsid(Boolean isid) {
        this.isid = isid;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
