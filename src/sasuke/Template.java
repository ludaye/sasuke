package sasuke;

/**
 * Created by Administrator on 2017/2/14.
 */
public class Template {
    private Boolean enabled;
    private String name;
    private String content;
    private String suffix;
    private String extension;

    public Template() {
    }

    public Template(Boolean enabled, String name, String content, String suffix, String extension) {
        this.enabled = enabled;
        this.name = name;
        this.content = content;
        this.suffix = suffix;
        this.extension = extension;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
