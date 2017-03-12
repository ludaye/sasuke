package sasuke;

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
}
