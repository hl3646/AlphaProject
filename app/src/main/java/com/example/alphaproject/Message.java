package com.example.alphaproject;

public class Message {
    private String value;
    private String key;

    public Message(String value, String key) {
        this.value = value;
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}