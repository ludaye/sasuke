package sasuke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
    public static final String DEFAULT_JDBC = "url=jdbc:mysql://localhost:3306\n" +
            "user=root\n" +
            "password=123456\n" +
            "entity.pathPattern=.*module1.*\n" +
            "entity.packagePath=com/lu/entity";
    public static final List<Template> TEMPLATE_LIST = new ArrayList<>();
    public static final Map<String, String> MAP = new HashMap<>();

    static {
        initList();
        initMap();
    }

    private static void initList() {
        Template template = new Template(true, "entity", "test", "java", "Entity");
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
