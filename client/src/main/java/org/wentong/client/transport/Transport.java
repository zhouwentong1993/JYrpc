package org.wentong.client.transport;

import org.wentong.protocol.RpcCommand;

import java.util.concurrent.CompletableFuture;

public interface Transport {

    /**
     * 发送请求
     */
    CompletableFuture<RpcCommand> send(RpcCommand request);

}
