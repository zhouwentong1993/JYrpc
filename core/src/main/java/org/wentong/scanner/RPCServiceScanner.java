package org.wentong.scanner;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.wentong.annotations.RPCServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class RPCServiceScanner {

    private static final Map<String, Object> serviceMap = new HashMap<>();

    static {
        String packageName = "org.wentong";
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        classes.forEach(c -> {
            RPCServiceImpl annotation = AnnotationUtil.getAnnotation(c, RPCServiceImpl.class);
            if (annotation != null) {
                try {
                    // 这里要把服务的父类名放进去，方便查找。
                    serviceMap.put(c.getInterfaces()[0].getName(), c.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    log.error("初始化 RPC 服务失败, 服务名:{}", c.getName(), e);
                }
            }
        });
    }

    public static Object getService(String serviceName) {
        return serviceMap.get(serviceName);
    }

}
