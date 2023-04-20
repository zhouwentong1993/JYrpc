package org.wentong.sample;

import org.wentong.annotations.RPCService;

@RPCService
public interface HelloService {
    String sayHello(String name);
}
