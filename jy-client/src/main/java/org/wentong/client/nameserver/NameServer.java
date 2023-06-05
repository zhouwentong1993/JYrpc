package org.wentong.client.nameserver;

import java.net.URI;
import java.util.List;

public interface NameServer {

    List<URI> lookupService(String serviceName) throws Exception;

}
