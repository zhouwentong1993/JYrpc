package org.wentong.client.exception;

public class ConnectedException extends RuntimeException{

    public ConnectedException(String message) {
        super(message);
    }

    public ConnectedException(Throwable cause) {
        super(cause);
    }
}
