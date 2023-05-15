package org.wentong.client.proxy;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.client.nameserver.MemoryNameServer;
import org.wentong.client.nameserver.NameServer;
import org.wentong.client.router.OneToOneRouter;
import org.wentong.client.router.Router;
import org.wentong.client.transport.Transport;
import org.wentong.constant.Constant;
import org.wentong.protocol.Header;
import org.wentong.protocol.RpcCommand;
import org.wentong.protocol.serialize.DeSerializer;
import org.wentong.protocol.serialize.SerializeFactory;
import org.wentong.protocol.serialize.Serializer;

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
            RpcCommand protocolData = getProtocolData(method, args, clazz, serializer);
            NameServer nameServer = new MemoryNameServer();
            Router router = new OneToOneRouter();
            Transport transport = router.selectOne(nameServer.lookupService("org.wentong.sample.HelloService").get(0));
            RpcCommand response = transport.send(protocolData).get();
            return deSerializer.deSerialize(response.getPayload(), Object.class);
        });
    }

    private static <T> RpcCommand getProtocolData(Method method, Object[] args, Class<T> clazz, Serializer serializer) {
        int hessianSerializer = Constant.ProtocolConstant.SerialType.hessian;
        log.info("proxy invoke on method: {}", method.getName());
        RpcCommand.RpcCommandBuilder builder = RpcCommand.builder();
        builder.magicNumber(Constant.ProtocolConstant.magicNumber);
        builder.protocolVersion(Constant.ProtocolConstant.protocolVersion);
        builder.messageId(IdUtil.getSnowflakeNextId());
        builder.messageType(Constant.ProtocolConstant.MessageType.invoke);
        builder.serializeType(hessianSerializer);
        builder.headerExtend(serializer.serialize(new Header(clazz.getName(), method.getName(), method.getParameterTypes())));
        builder.payload(serializer.serialize(args));
        RpcCommand build = builder.build();
        build.setHeaderSize(build.getHeaderTotalSize());
        build.setTotalSize(build.getHeaderSize() + build.getPayload().length);
        return build;
    }

}