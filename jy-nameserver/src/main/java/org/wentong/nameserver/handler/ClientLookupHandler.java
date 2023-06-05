package org.wentong.nameserver.handler;

import cn.hutool.core.collection.CollUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.wentong.constant.Constant;
import org.wentong.nameserver.longpolling.LongPollingRequest;
import org.wentong.nameserver.registry.NameserverRegistry;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

import java.net.URI;
import java.util.List;

/**
 * 客户端请求服务端地址处理器。
 * long polling 机制，默认 hang 住 15s。
 */
@Slf4j
public class ClientLookupHandler implements MessageTypeHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, RpcCommand msg, RpcProtocolBuilder rpcProtocolBuilder) throws Exception {
        log.info("收到客户端请求服务端地址的请求，请求内容为：{}", msg);
        byte[] payload = msg.getPayload();
        String serviceName = (String) rpcProtocolBuilder.deSerializer().deSerialize(payload, String.class);
        List<URI> server = NameserverRegistry.getServer(serviceName);
        if (CollUtil.isEmpty(server)) {
            LongPollingRequest<String, List<URI>> request = new LongPollingRequest<>(NameserverRegistry::getServer, 15 * 1000);
            ctx.writeAndFlush(rpcProtocolBuilder.getProtocolData(request.execute(serviceName)));
        } else {
            ctx.writeAndFlush(rpcProtocolBuilder.getProtocolData(server));
        }
    }

    @Override
    public boolean accept(RpcCommand rpcCommand) {
        return rpcCommand.getMessageType() == Constant.ProtocolConstant.MessageType.client_lookup;
    }
}