package org.wentong.server.network.bio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.wentong.dispatcher.Invoker;
import org.wentong.dispatcher.Parser;
import org.wentong.server.network.Server;
import org.wentong.protocol.RpcProtocolBuilder;
import org.wentong.thread.ServiceThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class BioServer extends ServiceThread implements Server {

    private final RpcProtocolBuilder rpcProtocolBuilder;

    public BioServer(RpcProtocolBuilder rpcProtocolBuilder) {
        this.rpcProtocolBuilder = rpcProtocolBuilder;
    }

    @Override
    public String getServiceName() {
        return "BioServer";
    }

    /**
     * start a new server
     */
    @Override
    public void startServer() throws IOException {
        start();
    }

    @SneakyThrows
    @Override
    public void doService() {
        int port = 8088;
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Server start on port: {}", port);
            while (true) {
                Socket socket = server.accept();
                log.info("Server accept a new connection: {}", socket);
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                log.info("Server receive data length: {}", len);
                byteArrayOutputStream.write(buffer, 0, len);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                log.info("trans data length: {}", byteArray.length);

                // bio server 实现得比较粗糙。
//                RpcProtocol rpcProtocol = rpcProtocolBuilder.validProtocolData(byteArray);
//
//                DeSerializer deSerializer = this.rpcProtocolBuilder.deSerializer();
//                Serializer serializer = this.rpcProtocolBuilder.serializer();
//
//                Header header = (Header) deSerializer.deSerialize(rpcProtocol.getHeaderExtend(), Header.class);
//                Object[] args = (Object[]) deSerializer.deSerialize(rpcProtocol.getPayload(), Object[].class);
//                String className = header.getClassName();
//                log.info("Server receive request, class:{}, method:{}", className, header.getMethodName());
//                Object service = RPCServiceScanner.getService(className);
//                Objects.requireNonNull(service);
//                Object result = service.getClass().getMethod(header.getMethodName(), header.getParameterTypes()).invoke(service, args);
                Object result = new Invoker().invoke(new Parser(rpcProtocolBuilder).parse(byteArray));
                log.info("Server receive data: {}", result);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(this.rpcProtocolBuilder.serializer().serialize(rpcProtocolBuilder.getProtocolData(result)));
                outputStream.flush();
                socket.close();
            }
        }
    }
}
