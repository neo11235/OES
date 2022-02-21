package com.fractals.OES.Classes;


public class Notification {
    private Integer notificationId;
    private String userId;
    private String courseId;
    private String message;
    private Integer readStatus;

    public Notification() {
    }

    public Notification(Integer notificationId, String userId, String courseId, String message, Integer readStatus) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.courseId = courseId;
        this.message = message;
        this.readStatus = readStatus;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
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

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }
}
