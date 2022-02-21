package com.fractals.OES.Classes;

import java.util.List;

public class QuestionPaper {
    private String examId;
    private List<String> questions;

    public QuestionPaper() {
    }

    public QuestionPaper(String examId, List<String> questions) {
        this.examId = examId;
        this.questions = questions;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
    public void insert(String s)
    {
        questions.add(s);
    }
}
