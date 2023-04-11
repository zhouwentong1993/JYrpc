package org.wentong.protocal;

public class RpcProtocolFactory {

    // todo
    public static RpcProtocol getProtocolData(Object data) {
        return RpcProtocol.builder()
                .magicNumber(0x12345678)

                .build();
    }

}
