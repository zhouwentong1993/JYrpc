package org.wentong.scanner;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.annotations.message.MessageHandler;
import org.wentong.server.network.netty.message.MessageTypeHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class MessageHandlerScanner {

    private static final Map<String, MessageTypeHandler> HANDLER_MAP = new HashMap<>();

    static {
        String packageName = "org.wentong";
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        classes.forEach(c -> {
            MessageHandler annotation = AnnotationUtil.getAnnotation(c, MessageHandler.class);
            if (annotation != null) {
                try {
                    HANDLER_MAP.put(c.getName(), (MessageTypeHandler) c.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    log.error("初始化消息处理器失败，消息名称：{}", c.getName(), e);
                }
            }
        });
    }

    public static MessageTypeHandler getHandler(String handlerName) {
        return HANDLER_MAP.get(handlerName);
    }

    public static List<MessageTypeHandler> allHandlers() {
        return HANDLER_MAP.values().stream().toList();
    }

}
