package sasuke;

import java.util.LinkedList;

public class ProjectModules {
    private LinkedList<String> sourcePaths = new LinkedList<>();
    private LinkedList<String> resourcePaths = new LinkedList<>();

    public LinkedList<String> getSourcePaths() {
        return sourcePaths;
    }

    public void setSourcePaths(LinkedList<String> sourcePaths) {
        this.sourcePaths = sourcePaths;
    }

    public LinkedList<String> getResourcePaths() {
        return resourcePaths;
    }

    public void setResourcePaths(LinkedList<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }
}
