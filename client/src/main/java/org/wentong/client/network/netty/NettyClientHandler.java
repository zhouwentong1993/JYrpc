package org.wentong.client.network.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.NonNull;
import org.wentong.protocol.RpcProtocolBuilder;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @NonNull
    private final byte[] data;
    @NonNull
    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NettyClientHandler(@NonNull byte[] data, @NonNull RpcProtocolBuilder rpcProtocolBuilder) {
        this.data = data;
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(data);
    }
}
