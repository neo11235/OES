package com.fractals.OES.Classes;

public class Course
{
    private String courseId;
    private String courseName;
    private String userId;

    public Course() {
    }

    public Course(String courseId, String courseName, String userId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "('"+courseId+"','"
                +courseName+"','"
                +userId+"')";
    }
}
