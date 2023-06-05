package org.wentong.nameserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.wentong.nameserver.scanner.NameserverMessageHandlerScanner;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

@Slf4j
public class NameServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NameServerHandler(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcCommand rpcCommand = (RpcCommand) msg;
        NameserverMessageHandlerScanner.allHandlers().stream()
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
}
