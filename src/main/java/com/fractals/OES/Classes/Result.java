package com.fractals.OES.Classes;

public class Result {
    private String examId;
    private String userId;
    private Integer score;

    public Result() {
    }

    public Result(String examId, String userId, Integer score) {
        this.examId = examId;
        this.userId = userId;
        this.score = score;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "('"+examId+"','"+userId+"',"+score.toString()+")";
    }
}
