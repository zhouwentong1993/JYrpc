package org.wentong.client.transport;

import org.wentong.client.network.netty.NettyClient;

import java.net.URI;

/**
 * 创建一个 netty transport，背后创建了一个 netty client
 */
public class NettyTransportFactory {

    public static Transport create(URI uri) throws Exception {
        NettyClient nettyClient = new NettyClient(uri);
        return new NettyTransport(nettyClient.getChannel(), nettyClient.getInFlightRequests());
    }
}
