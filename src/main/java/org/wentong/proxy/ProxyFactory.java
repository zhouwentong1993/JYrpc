package org.wentong.proxy;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.constant.Constant;
import org.wentong.network.client.Client;
import org.wentong.network.client.bio.BioClient;
import org.wentong.protocol.Header;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.SerializeFactory;
import org.wentong.serialize.Serializer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 获取代理对象工厂
 */
@Slf4j
public class ProxyFactory {

    public static <T> T refer(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            Serializer serializer = SerializeFactory.getSerializer(Constant.ProtocolConstant.SerialType.hessian);
            DeSerializer deSerializer = SerializeFactory.getDeSerializer(Constant.ProtocolConstant.SerialType.hessian);
            RpcProtocol protocolData = getProtocolData(method, args, clazz, serializer);
            Client client = new BioClient(new RpcProtocolBuilder(serializer, deSerializer));
            byte[] send = client.send(serializer.serialize(protocolData));
            RpcProtocol rpcProtocol = (RpcProtocol) deSerializer.deSerialize(send, RpcProtocol.class);
            return (T) (deSerializer.deSerialize(rpcProtocol.getPayload(), Object.class));
        });
    }

    private static <T> RpcProtocol getProtocolData(Method method, Object[] args, Class<T> clazz, Serializer serializer) {
        int hessianSerializer = Constant.ProtocolConstant.SerialType.hessian;
        log.info("proxy invoke on method: {}", method.getName());
        RpcProtocol.RpcProtocolBuilder builder = RpcProtocol.builder();
        builder.magicNumber(Constant.ProtocolConstant.magicNumber);
        builder.protocolVersion(Constant.ProtocolConstant.protocolVersion);
        builder.messageId(IdUtil.getSnowflakeNextId());
        builder.messageType(Constant.ProtocolConstant.MessageType.invoke);
        builder.serializeType(hessianSerializer);
        builder.headerExtend(serializer.serialize(new Header(clazz.getName(), method.getName())));
        builder.payload(serializer.serialize(args));
        RpcProtocol build = builder.build();
        build.setHeaderSize(build.getHeaderTotalSize());
        build.setTotalSize(build.getHeaderSize() + build.getPayload().length);
        return build;
    }

}