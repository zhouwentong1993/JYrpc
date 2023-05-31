package org.wentong.client.nameserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.wentong.protocol.netty.NettyHessianDecoder;
import org.wentong.protocol.netty.NettyHessianEncoder;

import java.net.URI;

public class LookupAddressService implements Runnable {

    private final URI uri;

    public LookupAddressService(URI uri) {
        this.uri = uri;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("decoder", new NettyHessianDecoder());
                    ch.pipeline().addLast("encoder", new NettyHessianEncoder());
                    ch.pipeline().addLast(new NameserverMsgHandler());
                }
            });
            ChannelFuture future = b.connect(uri.getHost(), uri.getPort()).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(null);
            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            throw new IllegalArgumentException(e);
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
