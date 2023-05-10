package org.wentong.client.transport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.wentong.client.InFlightRequests;
import org.wentong.client.response.ResponseFuture;
import org.wentong.protocol.RpcCommand;

import java.util.concurrent.CompletableFuture;

public class NettyTransport implements Transport {

    private final Channel channel;
    private final InFlightRequests inFlightRequests;

    public NettyTransport(Channel channel, InFlightRequests inFlightRequests) {
        this.channel = channel;
        this.inFlightRequests = inFlightRequests;
    }

    @Override
    public CompletableFuture<RpcCommand> send(RpcCommand request) {
        ChannelFuture channelFuture = channel.writeAndFlush(request);
        CompletableFuture<RpcCommand> future = new CompletableFuture<>();
        if (channelFuture.isSuccess()) {
            inFlightRequests.put(request.getMessageId(), new ResponseFuture(future));
        } else {
            return CompletableFuture.failedFuture(channelFuture.cause());
        }
        return future;
    }
}
