package org.wentong.client.router;

import org.wentong.client.transport.Transport;

import java.net.URI;

public interface Router {

    /**
     * 选择一个 Transport
     */
    Transport selectOne(URI uri);

}
