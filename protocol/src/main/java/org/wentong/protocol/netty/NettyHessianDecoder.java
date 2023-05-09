package org.wentong.protocol.netty;

import com.caucho.hessian.io.Hessian2Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.wentong.protocol.serialize.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class NettyHessianDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes, 0, length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Hessian2Input hessian2Input = new Hessian2Input(byteArrayInputStream);
        try {
            Object o = hessian2Input.readObject();
            list.add(o);
            hessian2Input.close();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }
}
