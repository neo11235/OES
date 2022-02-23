package com.fractals.OES.Classes;

public class FrResult {
    private String userName;
    private String email;
    private Integer score;

    public FrResult() {
    }

    public FrResult(String userName, String email, Integer score) {
        this.userName = userName;
        this.email = email;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
