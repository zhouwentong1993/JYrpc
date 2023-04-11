package org.wentong.network.client.bio;

import cn.hutool.core.io.IoUtil;
import org.wentong.network.client.Client;

import java.net.Socket;

/**
 * bio client，这里用的是 per send per connect 的方式
 */
public class BioClient implements Client {

    @Override
    public byte[] send(byte[] data) throws Exception {
        try (Socket socket = new Socket("localhost", 8088)) {
            System.out.println("connected to server");
            socket.getOutputStream().write(data);
            return IoUtil.readBytes(socket.getInputStream());
        }
    }
}
