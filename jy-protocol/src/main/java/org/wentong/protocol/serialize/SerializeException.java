package org.wentong.protocol.serialize;

public class SerializeException extends RuntimeException{

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializeException(Throwable cause) {
        super(cause);
    }

    public SerializeException() {
        super();
    }

}
