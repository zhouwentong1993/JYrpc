package org.wentong.client;

public interface SendCallback {

    void onSuccess(SendResult result);

    void onFailure(Throwable throwable);

}
