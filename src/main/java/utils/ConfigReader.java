package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Utility class to read properties from the "config.properties" file.
 * Loads the properties once in a static block and provides a method to access them.
 */
public class ConfigReader {
    private static Properties properties;
    private static final Logger logger=Logger.getLogger(ConfigReader.class.getName());
    // Static block to load properties at class initialization

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"failed to load config.properties",e);
        }
    }

    /**
     * Retrieves the value associated with the given key from the loaded properties.
     *
     * @param key the property key to retrieve
     * @return the value associated with the key, or {@code null} if the key is not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
