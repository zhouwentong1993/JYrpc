package org.wentong.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wentong.protocol.RpcCommand;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Getter
public class ResponseFuture {

    private CompletableFuture<RpcCommand> future;

}
