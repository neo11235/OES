package com.fractals.OES.Classes;

import java.util.List;

public class AnswerScript {
    private String examId;
    private String userId;
    private List<Answer> answers;

    public AnswerScript() {
    }

    public AnswerScript(String examId, String userId, List<Answer> answers) {
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
