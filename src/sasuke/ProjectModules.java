package sasuke;

import java.util.ArrayList;
import java.util.List;

public class ProjectModules {
    private List<String> sourcePaths = new ArrayList<>();
    private List<String> resourcePaths = new ArrayList<>();

    public List<String> getSourcePaths() {
        return sourcePaths;
    }

    public void setSourcePaths(List<String> sourcePaths) {
        this.sourcePaths = sourcePaths;
    }

    public List<String> getResourcePaths() {
        return resourcePaths;
    }

    public void setResourcePaths(List<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }
}
