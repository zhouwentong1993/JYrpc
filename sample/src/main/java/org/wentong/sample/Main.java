package org.wentong.sample;

import org.wentong.client.bio.BioClient;
import org.wentong.client.proxy.ProxyFactory;
import org.wentong.network.server.netty.NettyServer;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.impl.hessian.HessianDeserializer;
import org.wentong.protocol.serialize.impl.hessian.HessianSerializer;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        HessianSerializer<RpcProtocol> serializer = new HessianSerializer<>();
        HessianDeserializer<RpcProtocol> deSerializer = new HessianDeserializer<>();
        RpcProtocolBuilder rpcProtocolBuilder = new RpcProtocolBuilder(serializer, deSerializer);

        StartUp startUp = new StartUp(new NettyServer(rpcProtocolBuilder), new BioClient(rpcProtocolBuilder), serializer,
                deSerializer, rpcProtocolBuilder);
        startUp.startServer();

        // 确保 server 启动完成
        TimeUnit.SECONDS.sleep(3);

        HelloService refer = ProxyFactory.refer(HelloService.class);
        String wentong = refer.sayHello("wentong");
        System.out.println(wentong);

    }

}
