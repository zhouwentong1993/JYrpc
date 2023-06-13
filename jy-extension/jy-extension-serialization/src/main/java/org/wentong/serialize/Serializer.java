package org.wentong.serialize;

/**
 * 序列化接口抽象
 */
public interface Serializer<T> {

    /**
     * serialize t to byte array
     * @param t source object
     * @return byte array
     */
    byte[] serialize(T t);

}
