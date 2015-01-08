package com.acedened.vkutils;


import java.io.Serializable;

public class VKPost implements Serializable {

    private String text;
    private long authorID;

    public VKPost(String text, long authorID) {
        this.text = text;
        this.authorID = authorID;
    }

    public String getText() {
        return text;
    }

    public long getAuthorID() {
        return authorID;
    }
}
