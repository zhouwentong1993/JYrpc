package org.wentong.serialize.impl.hessian;

import com.wentong.serialize.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.Serializer;

class HessianSerializerTest {

    @Test
    void serialize() {
        User user = new User("wentong", 18, "bj", 13222222222L);
        Serializer<User> serializer = new HessianSerializer<>();
        byte[] bytes = serializer.serialize(user);
        Assertions.assertTrue(bytes.length > 0);
        DeSerializer<User> deSerializer = new HessianDeserializer<>();
        User user1 = deSerializer.deSerialize(bytes, User.class);
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("wentong", user1.getName());
        Assertions.assertEquals(18, user1.getAge());
    }
}