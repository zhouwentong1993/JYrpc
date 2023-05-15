package org.wentong.client.network.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.wentong.client.InFlightRequests;
import org.wentong.client.response.ResponseFuture;
import org.wentong.protocol.RpcCommand;

import java.util.Objects;

@Slf4j
public class NettyClientResponseHandler extends ChannelInboundHandlerAdapter {

    private final InFlightRequests inFlightRequests;

    public NettyClientResponseHandler(InFlightRequests inFlightRequests) {
        this.inFlightRequests = inFlightRequests;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcCommand command = (RpcCommand) msg;
        log.info("Client receive response: [{}]", command);
        Objects.requireNonNull(command);
        ResponseFuture future = inFlightRequests.remove(command.getMessageId());
        if (future != null) {
            future.getFuture().complete(command);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}