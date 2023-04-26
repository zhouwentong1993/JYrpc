package org.wentong.client;

/**
 * 客户端接口
 */
public interface Client {

    /**
     * 发送请求
     */
    byte[] send(byte[] data) throws Exception;

}
