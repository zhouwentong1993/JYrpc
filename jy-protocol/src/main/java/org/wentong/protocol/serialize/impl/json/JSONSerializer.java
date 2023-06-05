package org.wentong.protocol.serialize.impl.json;

import com.alibaba.fastjson.JSON;
import org.wentong.protocol.serialize.Serializer;

public class JSONSerializer<T> implements Serializer<T> {
    @Override
    public byte[] serialize(T o) {
        if (o == null) {
            return new byte[]{};
        }
        return JSON.toJSONBytes(o);
    }
}
