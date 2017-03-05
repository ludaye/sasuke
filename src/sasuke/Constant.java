package sasuke;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String DEFAULT_JDBC = "url=\n" +
            "user=root\n" +
            "password=123456\n";

    public static final List<Template> TEMPLATE_LIST = new ArrayList<>();

    static {
        Template template = new Template(true, "test", "test", "java", "Entity");
        TEMPLATE_LIST.add(template);
    }
}
