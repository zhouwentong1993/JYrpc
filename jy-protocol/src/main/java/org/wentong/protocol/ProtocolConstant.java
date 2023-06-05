package org.wentong.protocol;

public class ProtocolConstant {

    public static long magicNumber = Long.MAX_VALUE;

    public static int protocolVersion = 1;

    public static class MessageType {
        public static int invoke = 1;

        public static int heartbeat = 2;
    }

}
