package org.wentong.network.server.bio;

import org.wentong.network.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer implements Server {
    // Q: 生成一个 BIO server
    // A: 1. 创建一个 ServerSocket
    //    2. 通过 ServerSocket.accept() 获取一个 Socket
    //    3. 通过 Socket.getInputStream() 获取一个 InputStream
    //    4. 通过 InputStream.read() 读取数据
    //    5. 通过 Socket.getOutputStream() 获取一个 OutputStream
    //    6. 通过 OutputStream.write() 写数据
    //    7. 关闭 Socket
    //    8. 重复 2-7


    /**
     * start a new server
     */
    @Override
    public void start() throws IOException {
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
                    out.println("服务器收到:\t" + content);
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
