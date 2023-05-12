package org.wentong.client.router;

import org.wentong.client.exception.ConnectedException;
import org.wentong.client.transport.NettyTransportFactory;
import org.wentong.client.transport.Transport;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一对一的路由策略，每个服务对应一个 Transport
 */
public class OneToOneRouter implements Router {

    private final Map<URI, Transport> map = new ConcurrentHashMap<>();

    @Override
    public Transport selectOne(URI uri) {
        return map.computeIfAbsent(uri, this::createTransport);
    }

    private Transport createTransport(URI uri) {
        try {
            return NettyTransportFactory.create(uri);
        } catch (Exception e) {
            throw new ConnectedException(e);
        }
    }

}
