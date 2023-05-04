package org.wentong.network.server;

import org.wentong.protocol.Header;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.protocol.serialize.DeSerializer;
import org.wentong.protocol.serialize.Serializer;
import org.wentong.scanner.RPCServiceScanner;

import java.util.Objects;

public interface Server {

    void startServer() throws Exception;

    void shutdown() throws Exception;

    default Object routeAndExec(byte[] byteArray, RpcProtocolBuilder rpcProtocolBuilder) throws Exception {
        RpcProtocol rpcProtocol = rpcProtocolBuilder.validProtocolData(byteArray);

        DeSerializer deSerializer = rpcProtocolBuilder.deSerializer();
        Serializer serializer = rpcProtocolBuilder.serializer();

        Header header = (Header) deSerializer.deSerialize(rpcProtocol.getHeaderExtend(), Header.class);
        Object[] args = (Object[]) deSerializer.deSerialize(rpcProtocol.getPayload(), Object[].class);
        String className = header.getClassName();
//        log.info("Server receive request, class:{}, method:{}", className, header.getMethodName());
        Object service = RPCServiceScanner.getService(className);
        Objects.requireNonNull(service);
        Object result = service.getClass().getMethod(header.getMethodName(), header.getParameterTypes()).invoke(service, args);
//        log.info("Server receive data: {}", result);
        return result;
    }

}
