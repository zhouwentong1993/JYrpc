package org.wentong.network.server.bio;

import lombok.SneakyThrows;
import org.wentong.network.server.Server;
import org.wentong.thread.ServiceThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
        System.out.println("Server started on port " + 8088);

        while (true) {
            try (
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
            ) {
                System.out.println("Accepted connection from: " + clientSocket.getInetAddress());

                String request = in.readLine();
                String response = "Bio data for user " + request; // 这里根据实际情况返回特定用户的bio
                out.write(response);
                out.flush();
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            }
        }
    }
}
