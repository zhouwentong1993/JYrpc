package org.wentong.sample;

import org.wentong.annotations.service.RPCService;
import org.wentong.client.callback.Callback;

import java.util.concurrent.CompletableFuture;

@RPCService
public interface HelloService {
    String sayHello(String name);

    CompletableFuture<String> asyncSayHello(String name, long millSeconds);

    CompletableFuture<String> asyncSyaHello1(String name, long millSeconds);

    CompletableFuture<String> asyncSyaHello2(String name, long millSeconds);

    String helloCallback(String name, Callback<String> callback);


}
