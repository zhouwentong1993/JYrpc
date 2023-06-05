package org.wentong.protocol.serialize.impl.json;

import com.alibaba.fastjson.JSON;
import org.wentong.protocol.serialize.DeSerializer;

public class JSONDeserializer<T> implements DeSerializer<T> {
    @Override
    public T deSerialize(byte[] arr, Class<T> clazz) {
        if (arr == null || arr.length == 0 || clazz == null) {
            return null;
        }
        // parse array to T use fastJSOn
        return JSON.parseObject(arr, clazz);
    }
}
