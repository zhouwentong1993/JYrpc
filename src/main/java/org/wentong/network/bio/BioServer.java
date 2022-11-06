package org.wentong.network.bio;

import lombok.SneakyThrows;

import java.net.ServerSocket;

public class BioServer {

    /**
     * start a new server
     */
    @SneakyThrows
    public void run() {
        ServerSocket ss = new ServerSocket(8088);
    }


}
