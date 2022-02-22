package com.fractals.OES.Classes;

public class DbAnswerScript {
    private String examId;
    private String userId;
    private String answers;

    public DbAnswerScript() {
    }

    public DbAnswerScript(String examId, String userId, String answers) {
        this.examId = examId;
        this.userId = userId;
        this.answers = answers;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "('"+examId+"','"+userId+"','"+answers+"')";
    }
}
