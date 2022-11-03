package org.wentong.protocal;

import lombok.Data;

/**
 * RPC 协议
 */
@Data
public class RPCProtocol {
    private long magicNumber;
    private long size;
    private long headSize;
    private int protocolVersion;
    private int messageType;
    private int serializeType;
    private long messageId;
    private String extend;
    private byte[] payload;
}
