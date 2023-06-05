package org.wentong.client.nameserver;

import java.net.URI;
import java.util.List;

/**
 * 基于内存的 NameServer，这里暂时写死，后面需要通过网络从注册中心获取
 */
public class MemoryNameServer implements NameServer{

    @Override
    public List<URI> lookupService(String serviceName) throws Exception {
        return List.of(URI.create("rpc://localhost:8088"));
    }

}
