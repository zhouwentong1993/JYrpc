package org.wentong.sample;

import lombok.extern.slf4j.Slf4j;
import org.wentong.client.proxy.ProxyFactory;
import org.wentong.network.server.netty.NettyServer;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.impl.hessian.HessianDeserializer;
import org.wentong.protocol.serialize.impl.hessian.HessianSerializer;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        HessianSerializer<RpcCommand> serializer = new HessianSerializer<>();
        HessianDeserializer<RpcCommand> deSerializer = new HessianDeserializer<>();
        RpcProtocolBuilder rpcProtocolBuilder = new RpcProtocolBuilder(serializer, deSerializer);

        StartUp startUp = new StartUp(new NettyServer(rpcProtocolBuilder), serializer,
                deSerializer, rpcProtocolBuilder);
        startUp.startServer();

        // 确保 server 启动完成
        TimeUnit.SECONDS.sleep(3);
        log.info("after sleep 3 seconds, sending request.");

        HelloService refer = ProxyFactory.refer(HelloService.class);
        String wentong = refer.sayHello("wentong");
        System.out.println(wentong);

    }

}
