package org.wentong.nameserver.handler;

import io.netty.channel.ChannelHandlerContext;
import org.wentong.annotations.MessageHandler;
import org.wentong.constant.Constant;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

@MessageHandler
public class ClientLookupHandler implements MessageTypeHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder) throws Exception {

    }

    @Override
    public boolean accept(RpcCommand rpcCommand) {
        return rpcCommand.getMessageType() == Constant.ProtocolConstant.MessageType.client_nameserver;
    }
}
