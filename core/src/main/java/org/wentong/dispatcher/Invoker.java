package org.wentong.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.wentong.scanner.RPCServiceScanner;

import java.util.Objects;

@Slf4j
public class Invoker {

    public Object invoke(Invokee invokee) throws Exception {
        Object service = RPCServiceScanner.getService(invokee.className);
        Objects.requireNonNull(service);
        Object result = service.getClass().getMethod(invokee.methodName, invokee.parameterTypes).invoke(service, invokee.args);
        log.info("Server receive data: {}", result);
        return result;
    }

}
