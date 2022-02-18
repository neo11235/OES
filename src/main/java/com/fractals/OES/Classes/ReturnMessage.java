package com.fractals.OES.Classes;

public class ReturnMessage {
    private String userName;
    private String message;
    private String sentTime;

    public ReturnMessage() {
    }

    public ReturnMessage(String userName, String message, String sentTime) {
        this.userName = userName;
        this.message = message;
        this.sentTime = sentTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }
}
