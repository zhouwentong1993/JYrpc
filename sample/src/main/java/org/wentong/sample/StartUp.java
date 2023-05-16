package org.wentong.sample;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.server.Server;
import org.wentong.network.server.netty.NettyServer;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.impl.hessian.HessianDeserializer;
import org.wentong.protocol.serialize.impl.hessian.HessianSerializer;

@Slf4j
public class StartUp {

    private final Server server;

    public StartUp(Server server) {
        this.server = server;

    }

    public void startServer() throws Exception {
        server.startServer();
    }

    public void shutdown() throws Exception {
        server.shutdown();
    }


    public static void main(String[] args) throws Exception {
        HessianSerializer<RpcCommand> serializer = new HessianSerializer<>();
        HessianDeserializer<RpcCommand> deSerializer = new HessianDeserializer<>();
        RpcProtocolBuilder rpcProtocolBuilder = new RpcProtocolBuilder(serializer, deSerializer);

        StartUp startUp = new StartUp(new NettyServer(rpcProtocolBuilder)
        );
        startUp.startServer();

//        Object send = startUp.send("hello");
//        log.info("received data:{}", send);
    }

}