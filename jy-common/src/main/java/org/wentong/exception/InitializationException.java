package org.wentong.exception;

public class InitializationException extends RuntimeException {

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(Throwable cause) {
        super(cause);
    }
}
