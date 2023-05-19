package org.wentong.nameserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.wentong.protocol.RpcCommand;

public class NameServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcCommand rpcCommand = (RpcCommand) msg;

    }
}
