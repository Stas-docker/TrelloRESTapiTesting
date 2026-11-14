package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    private static final String FILE_NAME = "config.properties";
    private static volatile ConfigReader instance;

    private ConfigReader() {
        try (final FileInputStream fileInputStream = new FileInputStream(FILE_NAME)) {
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Config file '" + FILE_NAME + "' not found. Falling back to Environment/System properties.");
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to load properties from " + FILE_NAME, ioe);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    public String getProperty(final String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            value = System.getenv(key);
        }

        if (value == null || value.isBlank()) {
            value = System.getProperty(key);
        }

        if (value == null || value.isBlank()) {
            throw new RuntimeException("Property '" + key + "' not found in config file, Environment variables, or System properties!");
        }

        return value;
    }

    public static String getValue(final String key) {
        return getInstance().getProperty(key);
    }
}
