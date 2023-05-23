package org.wentong.client.server;

import org.wentong.utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 客户端依赖的所有服务端标签，用来对 Server 寻址。
 */
public class ServerLabel {

    private static final List<String> SERVERS = new ArrayList<>();

    static {
        Properties properties = PropertiesUtils.loadProperties("server.properties");
        properties.values().forEach(v -> SERVERS.add((String) v));
    }

    public static List<String> getServers() {
        return SERVERS;
    }

}
