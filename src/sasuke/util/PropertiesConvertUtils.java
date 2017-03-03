package sasuke.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/2/14.
 */
public class PropertiesConvertUtils {
    public static Properties stringToProperties(String string) throws IOException {
        Properties result = new Properties();
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        result.load(inputStream);
        inputStream.close();
        return result;
    }
}
