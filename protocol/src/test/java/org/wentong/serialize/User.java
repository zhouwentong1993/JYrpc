package org.wentong.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private String name;
    private Name fullName;
    private int age;
    private String address;
    private long phone;

}
