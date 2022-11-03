package com.wentong.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String name;
    private int age;
    private String address;
    private long phone;

}
