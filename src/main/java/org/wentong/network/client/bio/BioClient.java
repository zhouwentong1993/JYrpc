package org.wentong.network.client.bio;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.client.Client;
import org.wentong.protocal.RpcProtocolBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 最大接收 4k 的数据
            byte[] buffer = new byte[4096];
            InputStream inputStream = socket.getInputStream();
            int length = inputStream.read(buffer);
            byteArrayOutputStream.write(buffer, 0, length);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            RpcProtocolBuilder.validProtocolData(byteArray);
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
