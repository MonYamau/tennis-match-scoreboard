package com.project.util;

import com.project.exception.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new ConfigurationException("File couldn't be found: " + fileName);
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new ConfigurationException("Couldn't load the file: " + fileName, e);
        }
    }
}
