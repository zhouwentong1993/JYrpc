package org.wentong.client.network.netty;

import io.netty.channel.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;

@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @NonNull
    private final Object data;
    @NonNull
    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NettyClientHandler(@NonNull Object data, @NonNull RpcProtocolBuilder rpcProtocolBuilder) {
        this.data = data;
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        Object result = rpcProtocolBuilder.deSerializer().deSerialize(rpcProtocol.getPayload(), Object.class);
        log.info("响应结果{}", result);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture channelFuture = ctx.writeAndFlush(data);
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> System.out.println("发送数据成功"));
    }


}
