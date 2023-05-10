package org.wentong.client.invoker;

import org.wentong.client.network.Client;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;

public class CallerFacade<I, R> {

    private final Client client;
    private final RpcProtocolBuilder rpcProtocolBuilder;

    public CallerFacade(Client client, RpcProtocolBuilder rpcProtocolBuilder) {
        this.client = client;
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    public R call(I i) throws Exception{
        RpcCommand protocolData = rpcProtocolBuilder.getProtocolData(i);
        byte[] send = client.send(rpcProtocolBuilder.serializer().serialize(protocolData));
        RpcCommand response = (RpcCommand) rpcProtocolBuilder.deSerializer().deSerialize(send, RpcCommand.class);
        return (R) rpcProtocolBuilder.deSerializer().deSerialize(response.getPayload(), Object.class);
    }

}
