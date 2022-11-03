package org.wentong.serialize.impl.json;

import com.wentong.serialize.User;
import org.junit.Assert;
import org.junit.Test;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.Serializer;

public class JSONDeserializerTest {

    @Test
    public void deSerialize() {
        Serializer<User> serializer = new JSONSerializer<>();
        byte[] bytes = serializer.serialize(new User("wentong", 18, "bj", 13222222222L));
        Assert.assertTrue(bytes.length > 0);
        DeSerializer<User> deSerializer = new JSONDeserializer<>();
        User user = deSerializer.deSerialize(bytes, User.class);
        Assert.assertEquals("wentong", user.getName());
    }

    @Test
    public void deSerializeNull() {
        DeSerializer<User> deSerializer = new JSONDeserializer<>();
        User user = deSerializer.deSerialize(null, User.class);
        Assert.assertNull(user);
    }

    @Test
    public void testSerializeNull() {
        Serializer<User> serializer = new JSONSerializer<>();
        byte[] bytes = serializer.serialize(null);
        Assert.assertEquals(0, bytes.length);
    }

}