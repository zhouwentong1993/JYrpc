package org.wentong.client.nameserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.wentong.protocol.RpcCommand;

@Slf4j
public class NameserverMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcCommand command = (RpcCommand) msg;
        log.info("客户端收到 nameserver 消息：{}", command);
        command.getPayload();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("LongPollingHandler 异常", cause);
    }
}
