package sasuke;

import java.util.ArrayList;
import java.util.List;

public class TaskResult {
    private List<String> success = new ArrayList<>();
    private List<String> failure = new ArrayList<>();
    private List<String> skip = new ArrayList<>();

    public List<String> getSuccess() {
        return success;
    }

    public void setSuccess(List<String> success) {
        this.success = success;
    }

    public List<String> getFailure() {
        return failure;
    }

    public void setFailure(List<String> failure) {
        this.failure = failure;
    }

    public List<String> getSkip() {
        return skip;
    }

    public void setSkip(List<String> skip) {
        this.skip = skip;
    }
}
