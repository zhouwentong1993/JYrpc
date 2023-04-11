package org.wentong.serialize.impl.hessian;

import com.caucho.hessian.io.Hessian2Input;
import lombok.SneakyThrows;
import org.wentong.serialize.DeSerializer;

import java.io.ByteArrayInputStream;

public class HessianDeserializer<T> implements DeSerializer<T> {

    @SneakyThrows
    @Override
    public T deSerialize(byte[] arr, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arr);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        T t = (T) hessian2Input.readObject();
        hessian2Input.close();
        return t;
    }
}
