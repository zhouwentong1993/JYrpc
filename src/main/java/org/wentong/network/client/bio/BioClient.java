package org.wentong.network.client.bio;

import lombok.extern.slf4j.Slf4j;
import org.wentong.network.client.Client;

import java.io.*;
import java.net.Socket;

/**
 * bio client，这里用的是 per send per connect 的方式
 */
@Slf4j
public class BioClient implements Client {

    @Override
    public byte[] send(byte[] data) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", 8088);
             BufferedReader in = new BufferedReader(new InputStreamReader(
                     socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = socket.getInputStream().read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            byte[] byteArray = outputStream.toByteArray();
            socket.getOutputStream().write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
