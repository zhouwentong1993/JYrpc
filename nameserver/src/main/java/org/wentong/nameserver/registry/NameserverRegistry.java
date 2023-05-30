package org.wentong.nameserver.registry;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心，负责维护核心数据结构
 */
public class NameserverRegistry {

    private static final Map<String, List<URI>> SERVER_MAP = new ConcurrentHashMap<>();

    private static final Map<String, List<URI>> CLIENT_MAP = new ConcurrentHashMap<>();

    private static final Map<String, List<String>> CLIENT_REGISTER_MAP = new ConcurrentHashMap<>();

    public static List<URI> getServer(String serviceName) {
        return SERVER_MAP.get(serviceName);
    }

    public static void registerServer(String serviceName, URI uri) {
        List<URI> uris = SERVER_MAP.get(serviceName);
        if (uris == null) {
            uris = List.of(uri);
        } else {
            uris.add(uri);
        }
        SERVER_MAP.put(serviceName, uris);
    }

    public static void registerClient(String clientId, URI uri) {
        List<URI> uris = CLIENT_MAP.get(clientId);
        if (uris == null) {
            uris = List.of(uri);
        } else {
            uris.add(uri);
        }
        CLIENT_MAP.put(clientId, uris);
    }

    public static void registerClient(String clientId, String serviceName) {
        List<String> serviceNames = CLIENT_REGISTER_MAP.get(clientId);
        if (serviceNames == null) {
            serviceNames = List.of(serviceName);
        } else {
            serviceNames.add(serviceName);
        }
        CLIENT_REGISTER_MAP.put(clientId, serviceNames);
    }

}
