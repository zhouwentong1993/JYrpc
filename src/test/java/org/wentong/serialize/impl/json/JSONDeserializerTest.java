package org.wentong.serialize.impl.json;

import com.wentong.serialize.Name;
import com.wentong.serialize.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.Serializer;

class JSONDeserializerTest {

    @Test
    void deSerialize() {
        Serializer<User> serializer = new JSONSerializer<>();
        Name name = new Name("wentong", "zhang");
        byte[] bytes = serializer.serialize(new User("wentong", name, 18, "bj", 13222222222L));
        Assertions.assertTrue(bytes.length > 0);
        DeSerializer<User> deSerializer = new JSONDeserializer<>();
        User user = deSerializer.deSerialize(bytes, User.class);
        Assertions.assertEquals("wentong", user.getName());
    }

    @Test
    void deSerializeNull() {
        DeSerializer<User> deSerializer = new JSONDeserializer<>();
        User user = deSerializer.deSerialize(null, User.class);
        Assertions.assertNull(user);
    }

    @Test
    void testSerializeNull() {
        Serializer<User> serializer = new JSONSerializer<>();
        byte[] bytes = serializer.serialize(null);
        Assertions.assertEquals(0, bytes.length);
    }

}