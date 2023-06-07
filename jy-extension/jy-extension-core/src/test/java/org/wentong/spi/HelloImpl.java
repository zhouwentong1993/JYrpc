package org.wentong.spi;

public class HelloImpl implements Hello {
    @Override
    public String say() {
        System.out.println("HelloImpl.say");
        return "HelloImpl.say";
    }
}
