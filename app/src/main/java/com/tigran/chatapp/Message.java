package com.tigran.chatapp;

import java.util.Date;

public class Message {

    String from;
    String to;
    private String textMassage;
    private long sendTime;

    public Message(String from, String to, String textMassage) {
        this.from = from;
        this.to = to;
        this.textMassage = textMassage;
        this.sendTime = new Date().getTime();
    }

    public boolean isSenderI(String user) {
        if (user == from) {
            return true;
        } else {
            return false;
        }
    }

    public String getTextMassage() {
        return textMassage;
    }

    public long getSendTime() {
        return sendTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
