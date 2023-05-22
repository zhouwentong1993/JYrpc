package org.wentong.server.network.netty.message;

import io.netty.channel.ChannelHandlerContext;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

public interface MessageTypeHandler {

    void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder ) throws Exception;

    boolean accept(RpcCommand rpcCommand);

}
