package org.wentong.client.nameserver.longpolling;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.thread.ServiceThread;
import org.wentong.utils.PropertiesUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LongPollingManager extends ServiceThread {

    @Override
    public String getServiceName() {
        return "LongPollingManager";
    }

    @Override
    public void doService() {

        List<URI> uris = parseNameserverAddr();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(uris.size());

        // 每隔 30s 执行一次，每次 long polling 15s
        uris.forEach(uri -> executor.schedule(new LongPollingService(uri), 30, TimeUnit.SECONDS));
    }


    private List<URI> parseNameserverAddr() {
        Properties properties = PropertiesUtils.loadProperties("nameserver.addresses");
        // 读取属性值
        String propertyValue = properties.getProperty("nameserver.addresses");
        if (StrUtil.isBlank(propertyValue)) {
            throw new IllegalArgumentException("nameserver.addresses can not be blank");
        }
        String[] servers = propertyValue.split(",");
        return Arrays.stream(servers).map(URI::create).toList();

    }

}
