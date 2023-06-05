package org.wentong.scanner;

import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.exception.InitializationException;
import org.wentong.server.network.netty.message.MessageTypeHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CoreMessageHandlerScanner {

    private static final Map<String, MessageTypeHandler> HANDLER_MAP = new HashMap<>();

    static {
        Class<MessageTypeHandler> interfaceClass = MessageTypeHandler.class;
        String packageName = MessageTypeHandler.class.getPackage().getName();
        Set<Class<?>> allClasses = ClassUtil.scanPackage(packageName);
        for (Class<?> clazz : allClasses) {
            if (interfaceClass.isAssignableFrom(clazz) && !interfaceClass.equals(clazz)) {
                try {
                    HANDLER_MAP.put(clazz.getName(), (MessageTypeHandler) clazz.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new InitializationException(e);
                }
            }
        }
    }

    public static MessageTypeHandler getHandler(String handlerName) {
        return HANDLER_MAP.get(handlerName);
    }

    public static List<MessageTypeHandler> allHandlers() {
        return HANDLER_MAP.values().stream().toList();
    }

}
