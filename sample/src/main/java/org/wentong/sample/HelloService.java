package org.wentong.sample;

import org.wentong.annotations.RPCService;

import java.util.concurrent.CompletableFuture;

@RPCService
public interface HelloService {
    String sayHello(String name);

    CompletableFuture<String> asyncSayHello(String name, long millSeconds);

    CompletableFuture<String> asyncSyaHello1(String name, long millSeconds);

    CompletableFuture<String> asyncSyaHello2(String name, long millSeconds);



}
