package org.wentong.network.client.bio;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.client.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

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
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 最大接收 4k 的数据
            byte[] buffer = new byte[4096];
            int length;
            InputStream inputStream = socket.getInputStream();
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
