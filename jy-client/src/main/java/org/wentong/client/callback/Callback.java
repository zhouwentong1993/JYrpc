package org.wentong.client.callback;

/**
 * 回调函数
 *
 * @param <R> request
 */
@FunctionalInterface
public interface Callback<R> {

    /**
     * 回调通知
     *
     * @param r 通知对象
     */
    void notify(R r);

}
