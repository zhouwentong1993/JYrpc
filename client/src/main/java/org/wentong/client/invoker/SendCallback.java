package org.wentong.client.invoker;

public interface SendCallback {

    void onSuccess(SendResult result);

    void onFailure(Throwable throwable);

}
