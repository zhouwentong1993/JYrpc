package org.wentong.dispatcher;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public final class Invokee {

    @NonNull
    String className;
    @NonNull
    String methodName;
    @NonNull
    Class<?>[] parameterTypes;
    @NonNull
    Object[] args;
}
