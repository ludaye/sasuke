package sasuke.common;

import sasuke.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static final String DEFAULT_JDBC = "url=jdbc:mysql://localhost:3306\n" +
            "user=root\n" +
            "password=123456\n" +
            "entity.pathPattern=.*module1.*\n" +
            "entity.packagePath=com/lu/entity";
    private static final String ENTITY_TEMPLATE = "package ${packageName};\n" +
            "\n" +
            "public class ${className}{\n" +
            "<#list properties as pro>\n" +
            "    /**\n" +
            "     * ${pro.remark}\n" +
            "     */\n" +
            "    private ${pro.type} ${pro.lowCamelName};\n" +
            "</#list>\n" +
            "\n" +
            "<#list properties as pro>\n" +
            "    /**\n" +
            "     * 获取${pro.remark}\n" +
            "     * @return ${pro.remark}\n" +
            "     */\n" +
            "    public ${pro.type} get${pro.upCamelName}(){\n" +
            "        return ${pro.lowCamelName};\n" +
            "    }\n" +
            "    \n" +
            "    /**\n" +
            "     * 设置${pro.remark}\n" +
            "     * @param ${pro.lowCamelName} ${pro.remark}\n" +
            "     */\n" +
            "    public void set${pro.upCamelName}(${pro.type} ${pro.lowCamelName}){\n" +
            "        this.${pro.lowCamelName} = ${pro.lowCamelName};\n" +
            "    }\n" +
            "</#list>\n" +
            "}";

    public static final List<Template> TEMPLATE_LIST = new ArrayList<>();
    public static final Map<String, String> MAP = new HashMap<>();

    static {
        initList();
        initMap();
    }

    private static void initList() {
        Template template = new Template(true, "entity", ENTITY_TEMPLATE, "java", "Entity");
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
