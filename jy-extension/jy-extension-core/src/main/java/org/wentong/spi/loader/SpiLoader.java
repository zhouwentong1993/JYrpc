package org.wentong.spi.loader;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.wentong.spi.annotations.SPI;
import org.wentong.spi.exception.SpiInitException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpiLoader {

    private static final String SPI_PREFIX = "META-INF/services/";

    private SpiLoader() {
    }

    private static final SpiLoader INSTANCE = new SpiLoader();

    public static SpiLoader getInstance() {
        return INSTANCE;
    }

    private static final Map<Class, Object> CONTAINER = new HashMap<>();

    static {
        String packageName = "org.wentong";
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        classes.forEach(c -> {
            SPI annotation = AnnotationUtil.getAnnotation(c, SPI.class);
            if (annotation != null) {
                String interfaceName = c.getName();
                String resourceName = SPI_PREFIX + interfaceName;
                String content = ResourceUtil.readUtf8Str(resourceName);
                if (StrUtil.isNotBlank(content)) {
                    try {
                        CONTAINER.put(c, Class.forName(content).getDeclaredConstructor().newInstance());
                    } catch (Exception e) {
                        throw new SpiInitException(e);
                    }
                }
            }
        });
    }

    public static <T> T load(final Class<T> clazz) {
        return (T) CONTAINER.get(clazz);
    }
}
