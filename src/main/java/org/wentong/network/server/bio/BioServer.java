package org.wentong.network.server.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.wentong.network.server.Server;
import org.wentong.thread.ServiceThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

@Slf4j
public class BioServer extends ServiceThread implements Server {

    @Override
    public String getServiceName() {
        return "BioServer";
    }

    /**
     * start a new server
     */
    @Override
    public void startServer() throws IOException {
        start();
    }

    @SneakyThrows
    @Override
    public void doService() {
        int port = 8088;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            log.info("Server start on port: {}", port);
            Socket socket;
            while (true) {
                socket = server.accept();
                try {
                    InputStream inputStream = socket.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int len;
//                    while ((len = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, len);
//                        if (outputStream.size() > 4096) { // 设置 4KB 的输出流缓冲区大小
//                            socket.setSendBufferSize(outputStream.size());
//                        }
//                    }
                    inputStream.read(buffer);
                    outputStream.write(buffer);
                    byte[] byteArray = outputStream.toByteArray();
                    log.info("Server receive data: {}", Arrays.toString(byteArray));
                    OutputStream outputStream1 = socket.getOutputStream();
                    outputStream1.write(byteArray);
                    outputStream1.flush();
                } catch (Exception e) {
                    log.error("Server error", e);
                }
            }
        } finally {
            if (server != null) {
                log.info("Server close");
                server.close();
            }
        }
    }
}
