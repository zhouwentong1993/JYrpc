package com.wentong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NormalTest {

    @Test
    void testSuperClass() {
        Class<B> bClass = B.class;
        Class<?>[] interfaces = bClass.getInterfaces();
        Assertions.assertEquals(1, interfaces.length);
    }

    interface A {
        void hi();
    }

    class B implements A{

        @Override
        public void hi() {

        }
    }

}
