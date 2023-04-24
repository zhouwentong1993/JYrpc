package org.wentong.constant;

public class Constant {

    public static class ProtocolConstant {
        public static long magicNumber = Long.MAX_VALUE;

        public static int protocolVersion = 1;

        public static class SerialType {
            public static int json = 1;
            public static int hessian = 2;
        }

        public static class MessageType {
            public static int invoke = 1;

            public static int heartbeat = 2;
        }
    }

}
