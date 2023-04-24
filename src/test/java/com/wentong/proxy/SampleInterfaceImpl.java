package com.wentong.proxy;

public class SampleInterfaceImpl implements SampleInterface{
    @Override
    public String hi(String name) {
        return "hi " + name;
    }
}
