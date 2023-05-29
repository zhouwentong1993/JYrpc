package org.wentong.server.network.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.scanner.CoreMessageHandlerScanner;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NettyServerHandler(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcCommand rpcCommand = (RpcCommand) msg;
        CoreMessageHandlerScanner.allHandlers().stream()
                .filter(messageTypeHandler -> messageTypeHandler.accept(rpcCommand))
                .findFirst()
                .ifPresent(messageTypeHandler -> {
                    try {
                        messageTypeHandler.handle(ctx, rpcCommand, rpcProtocolBuilder);
                    } catch (Exception e) {
                        log.error("处理消息失败，消息类型：{}", rpcCommand.getMessageType(), e);
                    }
                });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理消息失败", cause);
        ctx.close();
    }
}
