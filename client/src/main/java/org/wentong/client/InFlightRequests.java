package org.wentong.client;

import org.wentong.protocol.RpcProtocol;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来做异步调用
 */
public class InFlightRequests {

    private final Map<Long, CompletableFuture<RpcProtocol>> inFlightRequests = new ConcurrentHashMap<>();

    public void put(long requestId, CompletableFuture<RpcProtocol> future) {
        inFlightRequests.put(requestId, future);
    }

    public CompletableFuture<RpcProtocol> remove(long requestId) {
        return inFlightRequests.remove(requestId);
    }

}
