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
    private static final String ENTITY_TEMPLATE = "<#assign keys = propertyMap?keys/>\n" +
            "<#assign p1=\"\">\n" +
            "<#if propertyMap.id??>\n" +
            "    <#assign p1=\"implement Entity\">\n" +
            "</#if>\n" +
            "package ${packageName};\n" +
            "/**\n" +
            " * ${className} : ${remark}\n" +
            " * @author ${user}\n" +
            " * @version 1.0\n" +
            " * @since ${.now}\n" +
            " * ${schema} ${table}\n" +
            " * ${p1}\n" +
            " */\n" +
            "public class ${className}{\n" +
            "<#list keys as key>\n" +
            "<#assign pro = propertyMap[\"${key}\"]>\n" +
            "    private ${pro.type} ${pro.lowCamelName};\n" +
            "</#list>\n" +
            "\n" +
            "<#list keys as key>\n" +
            "<#assign pro = propertyMap[\"${key}\"]>\n" +
            "    /**\n" +
            "     * ${pro.remark}\n" +
            "     */\n" +
            "    public ${pro.type} get${pro.upCamelName}(){\n" +
            "        return this.${pro.lowCamelName};\n" +
            "    }\n" +
            "    \n" +
            "    public void set${pro.upCamelName}(${pro.type} ${pro.lowCamelName}){\n" +
            "        this.${pro.lowCamelName} = ${pro.lowCamelName};\n" +
            "    }\n" +
            "    \n" +
            "</#list>\n" +
            "}";

    public static final List<Template> TEMPLATE_LIST = new ArrayList<>();
    public static final Map<String, String> MAP = new HashMap<>();

    static {
        initList();
        initMap();
    }

    private static void initList() {
        TEMPLATE_LIST.add(new Template(true, "entity", ENTITY_TEMPLATE, "java", "Entity"));
        TEMPLATE_LIST.add(new Template(true, "mapper", "test", "xml", "Mapper"));
        TEMPLATE_LIST.add(new Template(true, "service", "test", "java", "Service"));
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
