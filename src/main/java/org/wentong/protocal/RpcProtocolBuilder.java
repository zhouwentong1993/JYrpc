package org.wentong.protocal;

import cn.hutool.core.util.IdUtil;
import org.wentong.message.MessageType;
import org.wentong.serialize.Serializer;
import org.wentong.serialize.SerializerAlgo;

import java.nio.charset.StandardCharsets;

public class RpcProtocolBuilder {

    private final Serializer serializer;

    public RpcProtocolBuilder(Serializer serializer) {
        this.serializer = serializer;
    }

    public RpcProtocol getProtocolData(Object data) {
        byte[] serializedData = serializer.serialize(data);
        RpcProtocol build = RpcProtocol.builder()
                .magicNumber(0x12345678)
                .protocolVersion(1)
                .messageType(MessageType.TEST_REQUEST)
                .serializeType(SerializerAlgo.HESSIAN)
                .messageId(IdUtil.getSnowflakeNextId())
                .headerExtend("{}".getBytes(StandardCharsets.UTF_8))
                .payload(serializedData)
                .build();
        build.setHeaderSize(build.getHeaderTotalSize());
        build.setTotalSize(build.getHeaderSize() + build.getPayload().length);
        return build;
    }
}
