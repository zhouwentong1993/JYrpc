package org.wentong.client.invoker;

import org.wentong.client.Client;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;

public class CallerFacade<I, R> {

    private final Client client;
    private final RpcProtocolBuilder rpcProtocolBuilder;

    public CallerFacade(Client client, RpcProtocolBuilder rpcProtocolBuilder) {
        this.client = client;
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    public R call(I i) throws Exception{
        RpcProtocol protocolData = rpcProtocolBuilder.getProtocolData(i);
        byte[] send = client.send(rpcProtocolBuilder.serializer().serialize(protocolData));
        RpcProtocol response = (RpcProtocol) rpcProtocolBuilder.deSerializer().deSerialize(send, RpcProtocol.class);
        return (R) rpcProtocolBuilder.deSerializer().deSerialize(response.getPayload(), Object.class);
    }

}
