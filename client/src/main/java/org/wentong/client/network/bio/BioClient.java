package org.wentong.client.network.bio;

import lombok.extern.slf4j.Slf4j;
import org.wentong.client.network.Client;
import org.wentong.protocol.RpcProtocol;
import org.wentong.protocol.RpcProtocolBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * bio client，这里用的是 per send per connect 的方式
 */
@Slf4j
public class BioClient implements Client {

    private final RpcProtocolBuilder protocolBuilder;

    public BioClient(RpcProtocolBuilder protocolBuilder) {
        this.protocolBuilder = protocolBuilder;
    }

    @Override
    public byte[] send(byte[] data) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", 8088);
        ) {
            log.info("send data length: {}", data.length);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 最大接收 4k 的数据
            byte[] buffer = new byte[4096];
            InputStream inputStream = socket.getInputStream();
            int length = inputStream.read(buffer);
            byteArrayOutputStream.write(buffer, 0, length);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            protocolBuilder.validProtocolData(byteArray);
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RpcProtocol send(RpcProtocol data) throws Exception {
        byte[] serialize = protocolBuilder.serializer().serialize(data);
        byte[] send = send(serialize);
        Objects.requireNonNull(send);
        return (RpcProtocol) protocolBuilder.deSerializer().deSerialize(send, RpcProtocol.class);
    }


}
