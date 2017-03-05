package sasuke;

/**
 * Created by Administrator on 2017/2/14.
 */
public class Template {
    private Boolean enabled;
    private String name;
    private String content;
    private String extension;
    private String suffix;


    public Template() {
    }

    public Template(Boolean enabled, String name, String content, String extension, String suffix) {
        this.enabled = enabled;
        this.name = name;
        this.content = content;
        this.extension = extension;
        this.suffix = suffix;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Template)) {
            return false;
        }
        Template template = (Template) obj;
        if (isEquals(template)) {
            return true;
        }
        return false;
    }

    private boolean isEquals(Template template) {
        return ((this.enabled == null && template.enabled == null) || (this.enabled != null && template.enabled != null && this.enabled.equals(template.enabled))) &&
                ((this.name == null && template.name == null) || (this.name != null && template.name != null && this.name.equals(template.name))) &&
                ((this.content == null && template.content == null) || (this.content != null && template.content != null && this.content.equals(template.content))) &&
                ((this.suffix == null && template.suffix == null) || (this.suffix != null && template.suffix != null && this.suffix.equals(template.suffix))) &&
                ((this.extension == null && template.extension == null) || (this.extension != null && template.extension != null && this.extension.equals(template.extension)));
    }

}
