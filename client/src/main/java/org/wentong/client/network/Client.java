package org.wentong.client.network;

import org.wentong.protocol.RpcCommand;

/**
 * 客户端接口
 */
public interface Client {

    /**
     * 发送请求
     */
    byte[] send(byte[] data) throws Exception;

    RpcCommand send(RpcCommand data) throws Exception;

}
