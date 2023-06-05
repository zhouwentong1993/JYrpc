package org.wentong.nameserver.longpolling;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.lang.System.currentTimeMillis;

public class LongPollingRequest<Req,Resp> {

    private final Function<Req,Resp> consumer;

    private final long timeoutInMills;

    public LongPollingRequest(Function<Req, Resp> consumer, long timeoutInMills) {
        if (timeoutInMills <= 0) {
            throw new IllegalArgumentException("timeoutInMills must be positive");
        }
        this.consumer = consumer;
        this.timeoutInMills = timeoutInMills;
    }

    @SneakyThrows
    public Resp execute(Req req) {
        long deadline = currentTimeMillis() + timeoutInMills;
        while (true) {
            if (currentTimeMillis() <= deadline) {
                Resp resp = consumer.apply(req);
                if (resp != null) {
                    return resp;
                } else {
                    TimeUnit.MILLISECONDS.sleep(1);
                }
            }
        }
    }

}
