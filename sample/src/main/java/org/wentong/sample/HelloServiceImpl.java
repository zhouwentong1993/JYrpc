package org.wentong.sample;

import lombok.SneakyThrows;
import org.wentong.annotations.RPCServiceImpl;
import org.wentong.client.callback.Callback;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RPCServiceImpl
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @SneakyThrows
    @Override
    public CompletableFuture<String> asyncSayHello(String name, long millSeconds) {
        TimeUnit.MILLISECONDS.sleep(millSeconds);
        return CompletableFuture.completedFuture("Hello " + name);
    }

    @SneakyThrows
    @Override
    public CompletableFuture<String> asyncSyaHello1(String name, long millSeconds) {
        TimeUnit.MILLISECONDS.sleep(millSeconds);
        return CompletableFuture.completedFuture("Hello1 " + name);
    }

    @SneakyThrows
    @Override
    public CompletableFuture<String> asyncSyaHello2(String name, long millSeconds) {
        TimeUnit.MILLISECONDS.sleep(millSeconds);
        return CompletableFuture.completedFuture("Hello2 " + name);
    }

    @Override
    public String helloCallback(String name, Callback<String> callback) {
        return "Hello callback" + name;
    }

}
