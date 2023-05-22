package org.wentong.server.network.netty.message;

import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;
import org.wentong.annotations.message.MessageHandler;
import org.wentong.constant.Constant;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

@MessageHandler
public class NameServerHandler implements MessageTypeHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder) {
        // do nothing

    }

    @Override
    public boolean accept(@NonNull RpcCommand rpcCommand) {
        return rpcCommand.getMessageType() == Constant.ProtocolConstant.MessageType.nameserver;
    }
}
