//package csc508;
package org;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Handles the configuration for connecting to the Groq API.
 */

public class Config {

    private static Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("config.properties not found in classpath");
            } else {
                props.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
