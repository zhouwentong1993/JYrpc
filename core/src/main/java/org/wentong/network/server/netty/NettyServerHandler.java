package org.wentong.network.server.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.wentong.dispatcher.Invoker;
import org.wentong.dispatcher.Parser;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public NettyServerHandler(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        RpcCommand rpcCommand = (RpcCommand) msg;
        Object result = new Invoker().invoke(new Parser(rpcProtocolBuilder).parse(rpcCommand));
        RpcCommand protocolData = rpcProtocolBuilder.getProtocolData(result);
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
