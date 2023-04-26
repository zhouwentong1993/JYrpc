package org.wentong.sample;

import org.wentong.annotations.RPCServiceImpl;

@RPCServiceImpl
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
