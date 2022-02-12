package com.fractals.OES.Classes;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;
    private String role;//either 'admin' 'teacher' else 'student'
    private String phoneNumber;

    public User() {
    }
    public User(String userId, String firstName, String lastName, String email, String password, String birthDate, String role, String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "('"+userId+"','"
                +firstName+"','"
                +lastName+"','"
                +email+"','"
                +password+"','"
                +birthDate+"','"
                +role+
                (phoneNumber==null?"":("','"+phoneNumber))
                +"')";
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
