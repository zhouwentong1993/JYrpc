package org.wentong.client;

import org.wentong.client.response.ResponseFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来做异步调用，承载请求与响应
 */
public class InFlightRequests {

    private final Map<Long, ResponseFuture> inFlightRequests = new ConcurrentHashMap<>();

    public void put(long requestId, ResponseFuture future) {
        inFlightRequests.put(requestId, future);
    }

    public ResponseFuture remove(long requestId) {
        return inFlightRequests.remove(requestId);
    }

}
