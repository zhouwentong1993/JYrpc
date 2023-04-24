package org.wentong.protocol;

import cn.hutool.core.util.IdUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.wentong.constant.Constant;
import org.wentong.message.MessageType;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.SerializeException;
import org.wentong.serialize.Serializer;

@Slf4j
public class RpcProtocolBuilder {

    private final Serializer serializer;

    private final DeSerializer deSerializer;

    public RpcProtocolBuilder(Serializer serializer, DeSerializer deSerializer) {
        this.serializer = serializer;
        this.deSerializer = deSerializer;
    }

    public RpcProtocol getProtocolData(Object data) {
        byte[] serializedData = serializer.serialize(data);
        byte[] headerData = serializer.serialize("{}");
        RpcProtocol build = RpcProtocol.builder()
                .magicNumber(Long.MAX_VALUE)
                .protocolVersion(1)
                .messageType(MessageType.TEST_REQUEST)
                .serializeType(Constant.ProtocolConstant.SerialType.hessian)
                .messageId(IdUtil.getSnowflakeNextId())
                .headerExtend(headerData)
                .payload(serializedData)
                .build();
        build.setHeaderSize(build.getHeaderTotalSize());
        build.setTotalSize(build.getHeaderSize() + build.getPayload().length);
        return build;
    }

    public void validProtocolData(@NonNull byte[] data) {
        RpcProtocol o = (RpcProtocol) deSerializer.deSerialize(data, RpcProtocol.class);
        long magicNumber = o.getMagicNumber();
        if (magicNumber != Long.MAX_VALUE) {
            throw new SerializeException("magic number is not correct");
        }
        int protocolVersion = o.getProtocolVersion();
        log.info("protocolVersion: {}", protocolVersion);
        int messageType = o.getMessageType();
        log.info("messageType: {}", messageType);
        int serializeType = o.getSerializeType();
        log.info("serializeType: {}", serializeType);
        long messageId = o.getMessageId();
        log.info("messageId: {}", messageId);
        long headerSize = o.getHeaderSize();
        log.info("headerSize: {}", headerSize);
        byte[] headerExtend = o.getHeaderExtend();
        if (headerExtend == null || Long.BYTES * 4 + Integer.BYTES * 3 + headerExtend.length != headerSize) {
            throw new SerializeException("header extend is not correct");
        }
        long totalSize = o.getTotalSize();
        log.info("totalSize: {}", totalSize);
        if (o.getPayload().length + headerSize != totalSize) {
            throw new SerializeException("total size is not correct");
        }
    }
}
