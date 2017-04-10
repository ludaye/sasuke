package sasuke;

import com.google.common.base.MoreObjects;

/**
 * Created by Administrator on 2017/3/12.
 */
public class WillDoTemplate extends Template {
    private String path;

    public WillDoTemplate() {

    }

    public WillDoTemplate(Template t, String path) {
        super(t.getEnabled(), t.getName(), t.getContent(), t.getExtension(), t.getSuffix());
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("enabled", getEnabled()).add("name", getName()).add("content", getContent())
                .add("extension", getExtension()).add("suffix", getSuffix()).add("path", path).toString();
    }
}
