package org.wentong.client.network.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.wentong.client.Client;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;

// Netty 实现的 client
public class NettyClient implements Client {

    private final RpcProtocolBuilder protocolBuilder;

    public NettyClient(RpcProtocolBuilder protocolBuilder) {
        this.protocolBuilder = protocolBuilder;
    }

    @Override
    public byte[] send(byte[] data) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new NettyClientHandler(data, protocolBuilder));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("localhost", 8088).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
        return new byte[0];
    }

    @Override
    public RpcProtocol send(RpcProtocol data) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new NettyClientHandler(data, protocolBuilder));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("localhost", 8088).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
        return null;
    }
}
