package com.fractals.OES.Classes;

public class StudentTakes {
    private String userId;
    private String courseId;
    public StudentTakes() {}
    public StudentTakes(String userId, String courseId) {
        this.userId = userId;
        this.courseId = courseId;
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

    @Override
    public String toString() {
        return "('"+userId+"','"+courseId+"')";
    }
}
