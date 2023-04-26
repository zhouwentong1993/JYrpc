package org.wentong.protocol.serialize.impl.hessian;

import com.caucho.hessian.io.Hessian2Output;
import lombok.SneakyThrows;
import org.wentong.protocol.serialize.Serializer;

import java.io.ByteArrayOutputStream;

/**
 * Hessian 序列化
 */
public class HessianSerializer<T> implements Serializer<T> {

    @SneakyThrows
    @Override
    public byte[] serialize(T t) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(outputStream);
        output.writeObject(t);
        output.flushBuffer();
        byte[] byteArray = outputStream.toByteArray();
        outputStream.close();
        return byteArray;
    }
}
