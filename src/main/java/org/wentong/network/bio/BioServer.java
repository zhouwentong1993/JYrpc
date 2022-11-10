package org.wentong.network.bio;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

    /**
     * start a new server
     */
    @SneakyThrows
    public void run() {
        ServerSocket ss = new ServerSocket(8088);
        Socket socket = ss.accept();
        while (true) {
            //获取写的数据,自动刷新
            BufferedReader in = null;
            //写数据
            PrintWriter out = null;

            try {
                //获取写的数据,自动刷新
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //写数据， 第二个参数，表示自动flush
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                String content = null;

                while (true) {
                    content = in.readLine();
                    if (content == null) {
                        break;
                    }
                    System.out.println("服务器收到:\t" + content);
                    out.println("逗比连接上了");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (out != null) {
                    out.close();
                }

                socket = null;
            }
        }
    }


}
