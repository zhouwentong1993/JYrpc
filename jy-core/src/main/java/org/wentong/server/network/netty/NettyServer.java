package org.wentong.server.network.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.wentong.server.network.Server;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.netty.NettyHessianDecoder;
import org.wentong.protocol.netty.NettyHessianEncoder;
import org.wentong.thread.ServiceThread;

import java.util.Objects;

/**
 * 通过 Netty 实现的服务端
 */
@Slf4j
public class NettyServer extends ServiceThread implements Server {

    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    EventExecutorGroup businessGroup = null;

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NettyServer(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void startServer() {
        start();
    }

    @Override
    public String getServiceName() {
        return "NettyServer";
    }

    @Override
    public void doService() {
        log.info("NettyServer start at 8088");
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup(8);
        businessGroup = new DefaultEventExecutorGroup(2); // 业务线程池


        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 注册自己的处理器
                            socketChannel.pipeline().addLast("decoder", new NettyHessianDecoder());
                            socketChannel.pipeline().addLast("encoder", new NettyHessianEncoder());
                            socketChannel.pipeline().addLast(businessGroup, new NettyServerHandler(rpcProtocolBuilder));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = serverBootstrap.bind(8088).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("NettyServer start error", e);
            System.exit(1);
        } finally {
            this.workerGroup.shutdownGracefully();
            this.bossGroup.shutdownGracefully();
        }

    }

    @Override
    public void shutdown() {
        if (Objects.nonNull(this.bossGroup)) {
            this.bossGroup.shutdownGracefully();
        }
        if (Objects.nonNull(this.workerGroup)) {
            this.workerGroup.shutdownGracefully();
        }
    }
}
