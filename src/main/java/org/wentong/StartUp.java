package org.wentong;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.client.Client;
import org.wentong.network.client.bio.BioClient;
import org.wentong.network.server.Server;
import org.wentong.network.server.bio.BioServer;
import org.wentong.protocal.RpcProtocol;
import org.wentong.protocal.RpcProtocolBuilder;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.Serializer;
import org.wentong.serialize.impl.json.JSONDeserializer;
import org.wentong.serialize.impl.json.JSONSerializer;

@Slf4j
public class StartUp {

    private final Server server;
    private final Client client;
    private final Serializer<RpcProtocol> serializer;
    private final DeSerializer<RpcProtocol> deSerializer;
    private final RpcProtocolBuilder protocolBuilder;

    public StartUp(Server server, Client client, Serializer<RpcProtocol> serializer, DeSerializer<RpcProtocol> deSerializer) {
        this.server = server;
        this.client = client;

        this.serializer = serializer;
        this.deSerializer = deSerializer;
        protocolBuilder = new RpcProtocolBuilder(serializer);
    }

    public void go() throws Exception {
        server.startServer();
    }

    public void send(Object data) throws Exception {

        byte[] send = client.send(serializer.serialize(protocolBuilder.getProtocolData(data)));
        RpcProtocol deserialize = deSerializer.deSerialize(send, RpcProtocol.class);
        log.info("received data:{}", deserialize);
    }

    public static void main(String[] args) throws Exception {
        StartUp startUp = new StartUp(new BioServer(), new BioClient(), new JSONSerializer<>(), new JSONDeserializer<>());
        startUp.go();
//        startUp.send("hello");
    }

}