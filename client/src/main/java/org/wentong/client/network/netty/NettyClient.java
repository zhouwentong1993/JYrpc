package org.wentong.client.network.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import org.wentong.client.InFlightRequests;
import org.wentong.protocol.netty.NettyHessianDecoder;
import org.wentong.protocol.netty.NettyHessianEncoder;

import java.net.URI;

// Netty 实现的 client
@Getter
public class NettyClient {

    private final InFlightRequests inFlightRequests;
    private final Channel channel;

    public NettyClient(URI uri) throws Exception {
        inFlightRequests = new InFlightRequests();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("decoder", new NettyHessianDecoder());
                    ch.pipeline().addLast("encoder", new NettyHessianEncoder());
                    ch.pipeline().addLast(new NettyClientResponseHandler(inFlightRequests));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(uri.getHost(), uri.getPort()).sync();
            channel = f.channel();

            if (channel == null || !channel.isActive()) {
                throw new IllegalStateException();
            }
    }
}
