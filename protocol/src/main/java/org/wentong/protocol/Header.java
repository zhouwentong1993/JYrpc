package org.wentong.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Header implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
}
