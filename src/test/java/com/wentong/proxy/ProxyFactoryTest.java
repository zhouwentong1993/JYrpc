package com.wentong.proxy;

import org.junit.jupiter.api.Test;
import org.wentong.proxy.ProxyFactory;
import org.wentong.sample.HelloService;

public class ProxyFactoryTest {

    @Test
    void testProxy() {
        HelloService refer = ProxyFactory.refer(HelloService.class);
        refer.sayHello("wentong");
    }

}
