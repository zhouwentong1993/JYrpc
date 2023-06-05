package org.wentong.dispatcher;

import lombok.NonNull;
import org.wentong.protocol.Header;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.DeSerializer;

public class Parser {

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public Parser(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }


    public Invokee parse(@NonNull byte[] byteArray) {
        RpcCommand rpcCommand = rpcProtocolBuilder.validProtocolData(byteArray);
        DeSerializer deSerializer = rpcProtocolBuilder.deSerializer();

        Header header = (Header) deSerializer.deSerialize(rpcCommand.getHeaderExtend(), Header.class);
        Object[] args = (Object[]) deSerializer.deSerialize(rpcCommand.getPayload(), Object[].class);
        String className = header.getClassName();
        return new Invokee(className, header.getMethodName(), header.getParameterTypes(), args);
    }
    public Invokee parse(@NonNull RpcCommand rpcCommand) {
        DeSerializer deSerializer = rpcProtocolBuilder.deSerializer();

        Header header = (Header) deSerializer.deSerialize(rpcCommand.getHeaderExtend(), Header.class);
        Object[] args = (Object[]) deSerializer.deSerialize(rpcCommand.getPayload(), Object[].class);
        String className = header.getClassName();
        return new Invokee(className, header.getMethodName(), header.getParameterTypes(), args);
    }

}
