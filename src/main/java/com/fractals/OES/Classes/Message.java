package com.fractals.OES.Classes;

import java.sql.Date;

public class Message {
    private String userId;
    private String courseId;
    private String message;
    private Integer messageId;
    private java.sql.Date sentTime;

    public Message() {
    }

    public Message(String userId, String courseId, String message, Integer messageId, Date sentTime) {
        this.userId = userId;
        this.courseId = courseId;
        this.message = message;
        this.messageId = messageId;
        this.sentTime = sentTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
}
