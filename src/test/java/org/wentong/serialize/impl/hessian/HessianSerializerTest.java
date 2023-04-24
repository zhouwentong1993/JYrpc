package org.wentong.serialize.impl.hessian;

import com.wentong.serialize.Name;
import com.wentong.serialize.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.Serializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HessianSerializerTest {

    @Test
    void serialize() {
        Name name = new Name("wentong", "zhang");
        User user = new User("wentong", name, 18, "bj", 13222222222L);
        Serializer<User> serializer = new HessianSerializer<>();
        byte[] bytes = serializer.serialize(user);
        Assertions.assertTrue(bytes.length > 0);
        DeSerializer<User> deSerializer = new HessianDeserializer<>();
        User user1 = deSerializer.deSerialize(bytes, User.class);
        assertNotNull(user1);
        assertEquals("wentong", user1.getName());
        assertEquals(18, user1.getAge());
        assertNotNull(user1.getName());
        assertEquals("wentong", user1.getFullName().getFirstName());
    }

    @Test
    void testArrayData() {
        Name name = new Name("wentong", "zhang");
        User user = new User("wentong", name, 18, "bj", 13222222222L);
        User[] users = new User[2];
        users[0] = user;
        Name name1 = new Name("wentong", "zhang");
        User user1 = new User("wen", name1, 13, "j", 132222L);
        users[1] = user1;

        Serializer<Object> serializer = new HessianSerializer<>();
        byte[] serialize = serializer.serialize(users);

        assertNotNull(serialize);

        DeSerializer<User[]> deSerializer = new HessianDeserializer<>();
        User[] userResult = deSerializer.deSerialize(serialize, User[].class);
        assertEquals(2, userResult.length);

        assertEquals("wentong", userResult[0].getFullName().getFirstName());

    }

}