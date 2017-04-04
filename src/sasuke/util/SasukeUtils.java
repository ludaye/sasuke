package sasuke.util;

import com.intellij.openapi.components.ServiceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sasuke.SasukeSettings;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SasukeUtils {

    public static final Properties PROPERTIES = stringToProperties(ServiceManager.getService(
            SasukeSettings.class).getJdbc());

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

    public static List<String> getSourceFolder(String moduleFilePath) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<String> strings = new ArrayList<>();
        try {
            DocumentBuilder dbd = dbf.newDocumentBuilder();
            Document doc = dbd.parse(new File(moduleFilePath));
            NodeList nodeList = doc.getElementsByTagName("sourceFolder");
            for (int i = 0, len = nodeList.getLength(); i < len; i++) {
                Element item = (Element) nodeList.item(i);
                strings.add(item.getAttribute("url"));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("解析文件出错" + e.getMessage(), e);
        }
        return strings;
    }
}
