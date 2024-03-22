package com.github.lorellw.dictionary3000.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Util {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/test/resources/app.properties"));
        } catch (IOException ignored) { }
    }

    public static String getPropertyByKey(String key) {
        return properties.getProperty(key);
    }
}
