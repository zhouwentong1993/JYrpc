package org.wentong.nameserver.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.wentong.constant.Constant;
import org.wentong.nameserver.registry.NameserverRegistry;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

import java.net.InetSocketAddress;
import java.net.URI;

@Slf4j
public class ServerRegisterHandler implements MessageTypeHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder) throws Exception {
        log.info("收到服务端注册请求，服务端信息为：{}", msg);
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("服务端注册地址为：{}", remoteAddress);
        NameserverRegistry.registerServer((String) rpcProtocolBuilder.deSerializer().deSerialize(msg.getPayload(), String.class), URI.create(remoteAddress.getHostString() + ":" + remoteAddress.getPort()));
        ctx.writeAndFlush(rpcProtocolBuilder.getProtocolData(null));
    }

    @Override
    public boolean accept(@NonNull RpcCommand rpcCommand) {
        return rpcCommand.getMessageType() == Constant.ProtocolConstant.MessageType.server_register;
    }
}
