package org.wentong.network.server.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.wentong.network.server.Server;
import org.wentong.thread.ServiceThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        ServerSocket serverSocket = new ServerSocket(8088);
        log.info("Server started on port:{}", 8088);


        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                log.info("Accepted connection from: {}", clientSocket.getInetAddress());
                log.info("Server thread is: {}", Thread.currentThread().getName());
                InputStream inputStream = clientSocket.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    clientSocket.getOutputStream().write(buffer);
                }
                byte[] data = outputStream.toByteArray();
                log.info("Received data: {}", new String(data));
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            }
        }
    }
}
