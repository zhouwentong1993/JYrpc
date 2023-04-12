package org.wentong.network.client.bio;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.network.client.Client;

import java.net.Socket;

/**
 * bio client，这里用的是 per send per connect 的方式
 */
@Slf4j
public class BioClient implements Client {

    @Override
    public byte[] send(byte[] data) throws Exception {
        try (Socket socket = new Socket("localhost", 8088)) {
            log.info("connected to server");
            log.info("Thread name: {}", Thread.currentThread().getName());
            socket.getOutputStream().write(data);
            return IoUtil.readBytes(socket.getInputStream());
        }
    }
}
