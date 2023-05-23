package org.wentong.client.nameserver;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存服务端地址，与 nameserver 交互
 */
public class ServerAddr {

    private static final Map<String, List<URI>> SERVER_ADDR_MAP = new ConcurrentHashMap<>();

}
