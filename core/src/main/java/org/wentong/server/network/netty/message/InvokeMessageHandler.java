package org.wentong.server.network.netty.message;

import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;
import org.wentong.annotations.MessageHandler;
import org.wentong.constant.Constant;
import org.wentong.dispatcher.Invoker;
import org.wentong.dispatcher.Parser;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

/**
 * 服务端的调用 Handler，负责路由到指定的方法，并将返回值写回。
 */
@MessageHandler
public class InvokeMessageHandler implements MessageTypeHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder) throws Exception {
        Object result = new Invoker().invoke(new Parser(rpcProtocolBuilder).parse(msg));
        RpcCommand protocolData = rpcProtocolBuilder.getProtocolData(result);
        protocolData.setMessageId(msg.getMessageId());
        ctx.writeAndFlush(protocolData);
    }

    @Override
    public boolean accept(@NonNull RpcCommand rpcCommand) {
        return rpcCommand.getMessageType() == Constant.ProtocolConstant.MessageType.invoke;
    }
}
