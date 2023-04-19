package org.wentong.network.server.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.wentong.network.server.Server;
import org.wentong.protocal.RpcProtocolBuilder;
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
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Server start on port: {}", port);
            while (true) {
                Socket socket = server.accept();
                log.info("Server accept a new connection: {}", socket);
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                log.info("Server receive data length: {}", len);
                byteArrayOutputStream.write(buffer, 0, len);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                RpcProtocolBuilder.validProtocolData(byteArray);

                log.info("Server receive data: {}", Arrays.toString(byteArray));
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(byteArray);
                outputStream.flush();
                socket.close();
            }
        }
    }
}
