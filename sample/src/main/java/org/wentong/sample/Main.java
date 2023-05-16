package org.wentong.sample;

import lombok.extern.slf4j.Slf4j;
import org.wentong.client.callback.Callback;
import org.wentong.client.proxy.ProxyFactory;
import org.wentong.network.server.netty.NettyServer;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.impl.hessian.HessianDeserializer;
import org.wentong.protocol.serialize.impl.hessian.HessianSerializer;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        HessianSerializer<RpcCommand> serializer = new HessianSerializer<>();
        HessianDeserializer<RpcCommand> deSerializer = new HessianDeserializer<>();
        RpcProtocolBuilder rpcProtocolBuilder = new RpcProtocolBuilder(serializer, deSerializer);

        StartUp startUp = new StartUp(new NettyServer(rpcProtocolBuilder)
        );
        startUp.startServer();

        // 确保 server 启动完成
//        TimeUnit.SECONDS.sleep(3);
//        log.info("after sleep 3 seconds, sending request.");

        HelloService refer = ProxyFactory.refer(HelloService.class);
        String wentong = refer.sayHello("wentong");
        System.out.println(wentong);
//        refer.helloCallback("wentong", s -> System.out.println("callback: " + s));
        Callback<String> callback = new Callback<>() {
            @Override
            public void notify(String s) {
                System.out.println("callback: " + s);
            }
        };
        refer.helloCallback("wentong", callback);

    }

}
