package org.wentong.serialize.impl.hessian;

import com.caucho.hessian.io.Hessian2Input;
import lombok.extern.slf4j.Slf4j;
import org.wentong.serialize.DeSerializer;
import org.wentong.serialize.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class HessianDeserializer<T> implements DeSerializer<T> {

    @Override
    public T deSerialize(byte[] arr, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arr);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        T t;
        try {
            t = (T) hessian2Input.readObject();
            hessian2Input.close();
        } catch (IOException e) {
            log.error("Hessian deSerialize error", e);
            throw new SerializeException(e);
        }
        return t;
    }
}
