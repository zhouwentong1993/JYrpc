package org.wentong.client;

import org.wentong.protocol.RpcProtocol;

/**
 * 客户端接口
 */
public interface Client {

    /**
     * 发送请求
     */
    byte[] send(byte[] data) throws Exception;

    RpcProtocol send(RpcProtocol data) throws Exception;

}
