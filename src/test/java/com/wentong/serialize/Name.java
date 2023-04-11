package com.wentong.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Name implements Serializable {
    private String firstName;
    private String lastName;
}
