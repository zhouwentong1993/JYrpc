package org.wentong.client.proxy;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.client.callback.Callback;
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
            if (methodHasParam(method, Callback.class)) {
                Callback callback = (Callback) args[args.length - 1];
                callback.notify(deSerializer.deSerialize(response.getPayload(), Object.class));
                return null;
            }
            return deSerializer.deSerialize(response.getPayload(), Object.class);
        });
    }

    private static boolean methodHasParam(Method method, Class clazz) {
        // 获取目标方法的参数类型列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 判断参数类型列表中是否包含指定类型
        return ArrayUtil.contains(parameterTypes, clazz);
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

        Object[] temp = new Object[args.length];
        ArrayUtil.copy(args, temp, args.length);
        if (methodHasParam(method, Callback.class)) {
            temp[temp.length - 1] = null;
        }
        builder.payload(serializer.serialize(temp));
        RpcCommand build = builder.build();
        build.setHeaderSize(build.getHeaderTotalSize());
        build.setTotalSize(build.getHeaderSize() + build.getPayload().length);
        return build;
    }

}