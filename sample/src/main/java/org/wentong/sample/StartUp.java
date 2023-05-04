package org.wentong.sample;

import lombok.extern.slf4j.Slf4j;
import org.wentong.client.Client;
import org.wentong.client.bio.BioClient;
import org.wentong.network.server.Server;
import org.wentong.network.server.netty.NettyServer;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.DeSerializer;
import org.wentong.protocol.serialize.Serializer;
import org.wentong.protocol.serialize.impl.hessian.HessianDeserializer;
import org.wentong.protocol.serialize.impl.hessian.HessianSerializer;

@Slf4j
public class StartUp {

    private final Server server;
    private final Client client;
    private final Serializer serializer;
    private final DeSerializer deSerializer;
    private final RpcProtocolBuilder protocolBuilder;

    public StartUp(Server server, Client client, Serializer<RpcProtocol> serializer, DeSerializer<RpcProtocol> deSerializer, RpcProtocolBuilder protocolBuilder) {
        this.server = server;
        this.client = client;

        this.serializer = serializer;
        this.deSerializer = deSerializer;
        this.protocolBuilder = protocolBuilder;
    }

    public void startServer() throws Exception {
        server.startServer();
    }

    public Object send(Object data) throws Exception {

        byte[] send = client.send(serializer.serialize(protocolBuilder.getProtocolData(data)));
        RpcProtocol deserialize = (RpcProtocol) deSerializer.deSerialize(send, RpcProtocol.class);
        Object result = deSerializer.deSerialize(deserialize.getPayload(), Object.class);
        log.info("received data:{}", deserialize);
        return result;
    }

    public void shutdown() throws Exception {
        server.shutdown();
    }



    public static void main(String[] args) throws Exception {
        HessianSerializer<RpcProtocol> serializer = new HessianSerializer<>();
        HessianDeserializer<RpcProtocol> deSerializer = new HessianDeserializer<>();
        RpcProtocolBuilder rpcProtocolBuilder = new RpcProtocolBuilder(serializer, deSerializer);

        StartUp startUp = new StartUp(new NettyServer(rpcProtocolBuilder), new BioClient(rpcProtocolBuilder), serializer,
                deSerializer, rpcProtocolBuilder);
        startUp.startServer();

//        Object send = startUp.send("hello");
//        log.info("received data:{}", send);
    }

}