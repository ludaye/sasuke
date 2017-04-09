package sasuke.util;

import com.google.common.base.Strings;
import com.intellij.openapi.components.ServiceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sasuke.ModuleProperties;
import sasuke.SasukeSettings;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class SasukeUtils {

    public static final Properties PROPERTIES = stringToProperties(ServiceManager.getService(
            SasukeSettings.class).getJdbc());
    public static final String MODULE_DIR = "$MODULE_DIR$";
    public static final String URL_PREFIX = "file://";

    public static Properties stringToProperties(String string) {
        Properties result = new Properties();
        try {
            InputStream inputStream = new ByteArrayInputStream(string.getBytes());
            result.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("解析出错 " + e.getMessage(), e);
        }
        return result;
    }

    public static ModuleProperties getSourceFolder(String moduleFilePath, String modulePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ModuleProperties moduleProperties = new ModuleProperties();
        try {
            DocumentBuilder dbd = dbf.newDocumentBuilder();
            Document doc = dbd.parse(new File(moduleFilePath));
            NodeList nodeList = doc.getElementsByTagName("sourceFolder");
            for (int i = 0, len = nodeList.getLength(); i < len; i++) {
                Element item = (Element) nodeList.item(i);
                String isTestSource = item.getAttribute("isTestSource");
                String type = item.getAttribute("type");
                Optional<String> url = Optional.ofNullable(item.getAttribute("url")).map(s -> s.replace(MODULE_DIR,
                        modulePath).replace(URL_PREFIX, ""));
                if (!Strings.isNullOrEmpty(isTestSource) && "false".equals(isTestSource)) {
                    item.getAttribute("url");
                    url.ifPresent(s -> moduleProperties.getSrc().add(s));
                    break;
                }
                if (!Strings.isNullOrEmpty(type) && "java-resource".equals(type)) {
                    url.ifPresent(s -> moduleProperties.getResources().add(s));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("解析文件出错" + e.getMessage(), e);
        }
        return moduleProperties;
    }
}
