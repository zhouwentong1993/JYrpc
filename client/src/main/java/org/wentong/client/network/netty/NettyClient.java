package org.wentong.client.network.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import org.wentong.client.network.Client;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.netty.NettyHessianDecoder;
import org.wentong.protocol.netty.NettyHessianEncoder;
import org.wentong.thread.ServiceThread;

// Netty 实现的 client
public class NettyClient extends ServiceThread implements Client {

    private final RpcProtocolBuilder protocolBuilder;

    public NettyClient(RpcProtocolBuilder protocolBuilder) {
        this.protocolBuilder = protocolBuilder;
    }

    @Override
    public byte[] send(byte[] data) throws Exception {
        return new byte[0];
    }

    @Override
    public RpcCommand send(RpcCommand data) throws Exception {

        return null;
    }

    @Override
    public String getServiceName() {
        return "NettyClient";
    }

    @SneakyThrows
    @Override
    public void doService() {
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
                    ch.pipeline().addLast(new NettyClientResponseHandler(null));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("localhost", 8088).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
