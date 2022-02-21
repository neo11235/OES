package com.fractals.OES.Classes;

public class Exam {
    private String examId;
    private String startTime;
    private String endTime;
    private String courseId;
    private String examName;

    public Exam() {
    }

    public Exam(String examId, String startTime, String endTime, String courseId, String examName) {
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseId = courseId;
        this.examName = examName;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
