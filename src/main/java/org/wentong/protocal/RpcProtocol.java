package org.wentong.protocal;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * RPC 协议
 */
@Data
@Builder
public class RpcProtocol implements Serializable {
    // Magic Number
    private long magicNumber;
    // RPC 协议版本
    private int protocolVersion;
    // 消息类型，标识这次请求的类型
    private int messageType;
    // 序列化类型
    private int serializeType;
    // 消息唯一 id
    private long messageId;
    // header 长度
    private long headerSize;
    // header 扩展字段
    private byte[] headerExtend;
    // 消息总长度（包括消息头和消息体）
    private long totalSize;
    // 消息体
    private byte[] payload;

    public long getHeaderTotalSize() {
        return Long.BYTES * 4 + Integer.BYTES * 3 + headerExtend.length;
    }

}
