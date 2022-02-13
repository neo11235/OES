package com.fractals.OES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CreateDatabase {
    @Autowired
    JdbcTemplate jdbcTemplate;
    String USER_OES="CREATE TABLE C##OES.USER_OES" +
            "(" +
            "USER_ID VARCHAR2(64) PRIMARY KEY," +
            "FIRST_NAME VARCHAR2(40) NOT NULL," +
            "LAST_NAME VARCHAR2(40) NOT NULL," +
            "EMAIL VARCHAR2(40) NOT NULL UNIQUE," +
            "PASSWORD VARCHAR2(40) NOT NULL UNIQUE," +
            "BIRTH_DATE DATE NOT NULL," +
            "ROLE VARCHAR2(10) NOT NULL," +
            "PHN_NUMBER VARCHAR2(16)" +
            ")";
    String ActiveUserInfo="CREATE TABLE C##OES.ACTIVE_USER_INFO" +
            "(" +
            "USER_ID VARCHAR2(64)," +
            "TOKEN VARCHAR2(64) PRIMARY KEY," +
            "CONSTRAINT USER_ID_FK FOREIGN KEY(USER_ID) REFERENCES USER_OES(USER_ID) ON DELETE  CASCADE " +
            ")";
    String Courses="CREATE TABLE C##OES.COURSES" +
            "(" +
            "COURSE_ID VARCHAR2(64) PRIMARY KEY," +
            "COURSE_NAME VARCHAR2(64)," +
            "USER_ID VARCHAR2(64)," +
            "CONSTRAINT COURSES_USER_ID_FK FOREIGN KEY(USER_ID) REFERENCES USER_OES(USER_ID) ON DELETE CASCADE\n" +
            ")";
    public void create()
    {

        jdbcTemplate.execute(USER_OES);
        jdbcTemplate.execute(ActiveUserInfo);
        jdbcTemplate.execute(Courses);
    }
}
