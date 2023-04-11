package org.wentong.protocal;

import lombok.Builder;
import lombok.Data;

/**
 * RPC 协议
 */
@Data
@Builder
public class RpcProtocol {
    // Magic Number
    private long magicNumber;
    // 消息总长度（包括消息头和消息体）
    private long totalSize;
    // header 长度
    private long headerSize;
    // RPC 协议版本
    private int protocolVersion;
    // 消息类型，标识这次请求的类型
    private int messageType;
    // 序列化类型
    private int serializeType;
    // 消息唯一 id
    private long messageId;
    // header 扩展字段
    private String headerExtend;
    // 消息体
    private byte[] payload;
}
