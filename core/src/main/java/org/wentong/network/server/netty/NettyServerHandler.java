package org.wentong.network.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.wentong.dispatcher.Invoker;
import org.wentong.dispatcher.Parser;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NettyServerHandler(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = ByteBufUtil.getBytes(byteBuf);
        Object result = new Invoker().invoke(new Parser(rpcProtocolBuilder).parse(bytes));
        RpcProtocol protocolData = rpcProtocolBuilder.getProtocolData(result);
        ChannelFuture channelFuture = ctx.writeAndFlush(protocolData);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务端发送消息成功");
            } else {
                System.out.println("服务端发送消息失败");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
