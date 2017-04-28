package sasuke;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestFreemaker {

    private static String template = "hello,${name}";

    public static void createHtmlFromString() {
        FileWriter out = null;
        try {
            //构造Configuration
            Configuration configuration = new Configuration();
            //构造StringTemplateLoader
            StringTemplateLoader loader = new StringTemplateLoader();
            //添加String模板
            loader.putTemplate("test", template);
            //把StringTemplateLoader添加到Configuration中
            configuration.setTemplateLoader(loader);

            Map<String, Object> root = new HashMap<>();
            root.put("name", "luluxiu");

            //获取模板
            Template template = configuration.getTemplate("test");
            //构造输出路
            out = new FileWriter("E:\\ideawokespace\\tt\\src\\test\\test-resources\\ss.txt");
            //生成HTML
            template.process(root, out);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        createHtmlFromString();
    }
}
