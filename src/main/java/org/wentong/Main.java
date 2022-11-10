package org.wentong;

import org.wentong.network.bio.BioServer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        BioServer bioServer = new BioServer();
        bioServer.run();
    }
}