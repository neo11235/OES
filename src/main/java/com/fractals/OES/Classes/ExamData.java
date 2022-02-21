package com.fractals.OES.Classes;

import java.util.List;

public class ExamData {
    private String startTime;//"yyyy-MM-dd:HH24-mm-ss" format
    private String endTime;//same format as before
    private String courseId;
    private String examName;
    private List<String> questionIds;

    public ExamData() {
    }

    public ExamData(String startTime, String endTime, String courseId, String examName, List<String> questionIds) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseId = courseId;
        this.examName = examName;
        this.questionIds = questionIds;
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

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }
}
