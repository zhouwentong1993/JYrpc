package com.wentong.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wentong.StartUp;
import org.wentong.network.client.bio.BioClient;
import org.wentong.network.server.bio.BioServer;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.proxy.ProxyFactory;
import org.wentong.sample.HelloService;
import org.wentong.serialize.impl.hessian.HessianDeserializer;
import org.wentong.serialize.impl.hessian.HessianSerializer;

public class ProxyFactoryTest {

    @Test
    void testProxy() throws Exception {

        HessianSerializer<RpcProtocol> serializer = new HessianSerializer<>();
        HessianDeserializer<RpcProtocol> deSerializer = new HessianDeserializer<>();
        RpcProtocolBuilder rpcProtocolBuilder = new RpcProtocolBuilder(serializer, deSerializer);

        StartUp startUp = new StartUp(new BioServer(rpcProtocolBuilder), new BioClient(rpcProtocolBuilder), serializer,
                deSerializer, rpcProtocolBuilder);
        startUp.startServer();

        HelloService refer = ProxyFactory.refer(HelloService.class);
        String wentong = refer.sayHello("wentong");
        Assertions.assertEquals("Hello wentong", wentong);
        startUp.shutdown();
        System.exit(0);

    }

}
