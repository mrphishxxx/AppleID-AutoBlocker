package com.acedened.vkutils;


import java.io.Serializable;

public class VKArgument implements Serializable {

    String name;
    String value;

    public VKArgument(String argumentName, Object argumentValue) {
        name = argumentName;
        value = argumentValue.toString();
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

}
