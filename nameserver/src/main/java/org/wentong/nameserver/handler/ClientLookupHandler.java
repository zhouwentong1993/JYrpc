package org.wentong.nameserver.handler;

import io.netty.channel.ChannelHandlerContext;
import org.wentong.constant.Constant;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

/**
 * 客户端请求服务端地址处理器。
 * long polling 机制，默认 hang 住 15s。
 */
public class ClientLookupHandler implements MessageTypeHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder) throws Exception {


    }

    @Override
    public boolean accept(RpcCommand rpcCommand) {
        return rpcCommand.getMessageType() == Constant.ProtocolConstant.MessageType.client_nameserver;
    }
}
