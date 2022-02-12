package com.fractals.OES.Classes;

public class LoginResponse {
    private String status;//must be either 'success' or 'failed'
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
