package org.wentong.client.nameserver.longpolling;

import java.net.URI;

public class LongPollingService {

    private final URI uri;

    public LongPollingService(URI uri) {
        this.uri = uri;
    }
}
