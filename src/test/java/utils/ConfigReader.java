package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try {
            final FileInputStream fileInputStream = new FileInputStream("config.properties");
            properties.load(fileInputStream);
        }
        catch (FileNotFoundException e){
            throw new RuntimeException("file not found");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static String getValue(final String key) {
       return properties.getProperty(key);
    }
}
