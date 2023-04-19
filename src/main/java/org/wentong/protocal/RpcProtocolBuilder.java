package org.wentong.protocal;

import cn.hutool.core.util.IdUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.wentong.message.MessageType;
import org.wentong.serialize.SerializeException;
import org.wentong.serialize.Serializer;
import org.wentong.serialize.SerializerAlgo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RpcProtocolBuilder {

    private final Serializer serializer;

    public RpcProtocolBuilder(Serializer serializer) {
        this.serializer = serializer;
    }

    public RpcProtocol getProtocolData(Object data) {
        byte[] serializedData = serializer.serialize(data);
        RpcProtocol build = RpcProtocol.builder()
                .magicNumber(Long.MAX_VALUE)
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

    public static void validProtocolData(@NonNull byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        if (byteBuffer.hasRemaining()) {
            long magicNumber = byteBuffer.getLong();
            if (magicNumber != Long.MAX_VALUE) {
                throw new SerializeException("magic number is not correct");
            }
            int protocolVersion = byteBuffer.getInt();
            log.info("protocolVersion: {}", protocolVersion);
            int messageType = byteBuffer.getInt();
            log.info("messageType: {}", messageType);
            int serializeType = byteBuffer.getInt();
            log.info("serializeType: {}", serializeType);
            long messageId = byteBuffer.getLong();
            log.info("messageId: {}", messageId);
            long headerSize = byteBuffer.getLong();
            log.info("headerSize: {}", headerSize);
            byteBuffer.position(byteBuffer.position() + (int) headerSize);
            long totalSize = byteBuffer.getLong();
            log.info("totalSize: {}", totalSize);
            byteBuffer.position((int) totalSize);
        } else {
            log.info("no data");
            throw new SerializeException("can't parse data from input");
        }
    }

}
