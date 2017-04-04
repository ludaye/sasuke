package sasuke;

import com.google.common.base.Objects;

import java.util.List;

public class Table {
    private String name;
    private String upCamelName;
    private String remark;
    private List<Column> columns;

    public Table() {

    }

    public Table(String name, String upCamelName, String remark, List<Column> columns) {
        this.name = name;
        this.upCamelName = upCamelName;
        this.remark = remark;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getUpCamelName() {
        return upCamelName;
    }

    public void setUpCamelName(String upCamelName) {
        this.upCamelName = upCamelName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", name).add("upCamelName", upCamelName).add("remark", remark).add("columns", columns).toString();
    }
}
