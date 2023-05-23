package org.wentong.client.nameserver.longpolling;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.thread.ServiceThread;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
public class LongPollingManager extends ServiceThread {

    @Override
    public String getServiceName() {
        return "LongPollingManager";
    }

    @Override
    public void doService() {
        List<URI> uris = parseNameserverAddr();
        uris.forEach(uri -> {
            LongPollingService longPollingService = new LongPollingService(uri);

        });
    }


    private List<URI> parseNameserverAddr() {
        ClassLoader classLoader = LongPollingManager.class.getClassLoader();
        // 使用 ClassLoader 加载资源文件
        try (InputStream inputStream = classLoader.getResourceAsStream("nameserver.properties")) {
            Properties properties = new Properties();
            // 加载 properties 文件
            properties.load(inputStream);
            // 读取属性值
            String propertyValue = properties.getProperty("nameserver.addresses");
            if (StrUtil.isBlank(propertyValue)) {
                throw new IllegalArgumentException("nameserver.addresses can not be blank");
            }
            String[] servers = propertyValue.split(",");
            return Arrays.stream(servers).map(URI::create).toList();
        } catch (IOException e) {
            log.error("parse nameserver address error", e);
            throw new IllegalArgumentException(e);
        }
    }

}
