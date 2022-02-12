package com.fractals.OES.Classes;

public class ActiveUserInfo {
    private String userId;
    private String token;

    public ActiveUserInfo() {
    }

    public ActiveUserInfo(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "('"+userId+"','"+token+"')";
    }
}
