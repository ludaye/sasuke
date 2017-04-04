package sasuke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
    public static final String DEFAULT_JDBC = "url=\n" +
            "user=root\n" +
            "password=123456\n";
    public static final List<Template> TEMPLATE_LIST = new ArrayList<>();
    public static final Map<String, String> MAP = new HashMap<>();
    public static final String MODULE_DIR = "$MODULE_DIR$";

    static {
        initList();
        initMap();
    }

    private static void initList() {
        Template template = new Template(true, "test", "test", "java", "Entity");
        TEMPLATE_LIST.add(template);
    }

    private static void initMap() {
        MAP.put("VARCHAR", "java.lang.String");
        MAP.put("CHAR", "java.lang.String");
        MAP.put("TEXT", "java.lang.String");
        MAP.put("INT", "java.lang.Integer");
        MAP.put("TINYINT", "java.lang.Integer");
        MAP.put("BIGINT", "java.lang.Long");
        MAP.put("LONGTEXT", "java.lang.String");
        MAP.put("MEDIUMTEXT", "java.lang.String");
    }
}
