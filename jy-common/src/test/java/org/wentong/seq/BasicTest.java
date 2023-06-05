package org.wentong.seq;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BasicTest {

    @Test
    void testListForEach() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Seq<Integer> seq = list::forEach;
        seq.consume(System.out::println);
    }
}
