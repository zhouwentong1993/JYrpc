package org.wentong.sample;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.server.Server;
import org.wentong.network.server.netty.NettyServer;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.DeSerializer;
import org.wentong.protocol.serialize.Serializer;
import org.wentong.protocol.serialize.impl.hessian.HessianDeserializer;
import org.wentong.protocol.serialize.impl.hessian.HessianSerializer;

@Slf4j
public class StartUp {

    private final Server server;
    private final Serializer serializer;
    private final DeSerializer deSerializer;
    private final RpcProtocolBuilder protocolBuilder;

    public StartUp(Server server, Serializer<RpcCommand> serializer, DeSerializer<RpcCommand> deSerializer, RpcProtocolBuilder protocolBuilder) {
        this.server = server;

        this.serializer = serializer;
        this.deSerializer = deSerializer;
        this.protocolBuilder = protocolBuilder;
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

        StartUp startUp = new StartUp(new NettyServer(rpcProtocolBuilder), serializer,
                deSerializer, rpcProtocolBuilder);
        startUp.startServer();

//        Object send = startUp.send("hello");
//        log.info("received data:{}", send);
    }

}