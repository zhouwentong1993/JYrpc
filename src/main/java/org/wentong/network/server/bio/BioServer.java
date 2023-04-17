package org.wentong.network.server.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.wentong.network.server.Server;
import org.wentong.thread.ServiceThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    String body;
                    while (true) {
                        body = in.readLine();
                        if (body == null) {
                            break;
                        }
                        body = body.replaceAll("\\[|\\]|\\s", ""); // 去掉空格和中括号
                        String[] strArr = body.split(",");
                        byte[] byteArray = new byte[strArr.length];
                        for (int i = 0; i < strArr.length; i++) {
                            byteArray[i] = Byte.parseByte(strArr[i].trim());
                        }
                        log.info("Server receive data: {}", body);
                        out.println(Arrays.toString(byteArray));
                    }

                } catch (Exception e) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
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
