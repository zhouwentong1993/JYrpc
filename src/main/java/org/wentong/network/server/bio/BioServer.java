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
            System.out.println("The time server is start in port : " + port);
            Socket socket;
            while (true) {
                socket = server.accept();
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    String currentTime = null;
                    String body = null;
                    while (true) {
                        body = in.readLine();
                        if (body == null) {
                            break;
                        }
                        System.out.println("The time server receive order : " + body);
                        currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                                System.currentTimeMillis()).toString() : "BAD ORDER";
                        out.println(currentTime);
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
                }            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
            }
        }
    }
}
