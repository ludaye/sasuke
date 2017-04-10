package sasuke;

import java.util.ArrayList;
import java.util.List;

public class ModuleProperties {
    private List<String> src = new ArrayList<>();
    private List<String> resources = new ArrayList<>();

    public List<String> getSrc() {
        return src;
    }

    public void setSrc(List<String> src) {
        this.src = src;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
