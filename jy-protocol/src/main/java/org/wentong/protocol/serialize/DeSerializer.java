package org.wentong.protocol.serialize;

public interface DeSerializer<T> {

    /**
     * convert byte array to T
     * @param arr byte array
     * @param clazz the T's type
     * @return T object
     */
    T deSerialize(byte[] arr, Class<T> clazz);

}
