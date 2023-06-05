package org.wentong.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public final class PropertiesUtils {

    public static Properties loadProperties(String propertiesName) {
        ClassLoader classLoader = PropertiesUtils.class.getClassLoader();
        // 使用 ClassLoader 加载资源文件
        try (InputStream inputStream = classLoader.getResourceAsStream(propertiesName)) {
            Properties properties = new Properties();
            // 加载 properties 文件
            properties.load(inputStream);
            return properties;

        } catch (IOException e) {
            log.error("parse nameserver address error", e);
            throw new IllegalArgumentException(e);
        }
    }

}
