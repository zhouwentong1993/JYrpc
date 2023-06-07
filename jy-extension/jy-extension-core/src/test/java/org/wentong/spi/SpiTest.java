package org.wentong.spi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wentong.spi.loader.SpiLoader;

public class SpiTest {

    @Test
    void testSpiLoad() {
        Hello hello = SpiLoader.load(Hello.class);
        String say = hello.say();
        Assertions.assertEquals("HelloImpl.say", say);
    }

}
