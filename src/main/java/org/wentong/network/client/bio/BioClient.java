package org.wentong.network.client.bio;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.client.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * bio client，这里用的是 per send per connect 的方式
 */
@Slf4j
public class BioClient implements Client {

    @Override
    public byte[] send(byte[] data) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", 8088);
        ) {
            log.info("send data length: {}", data.length);
            OutputStream outputStream1 = socket.getOutputStream();
            outputStream1.write(data);
            outputStream1.flush();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
//            while ((length = socket.getInputStream().read(buffer)) != -1) {
//                outputStream.write(buffer, 0, length);
//            }
            socket.getInputStream().read(buffer);
            outputStream.write(buffer);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
