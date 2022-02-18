package com.fractals.OES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public final class CreateDatabase {
    @Autowired
    private static JdbcTemplate jdbcTemplate;
    private static String USER_OES="CREATE TABLE C##OES.USER_OES" +
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
    private static String ActiveUserInfo="CREATE TABLE C##OES.ACTIVE_USER_INFO" +
            "(" +
            "USER_ID VARCHAR2(64)," +
            "TOKEN VARCHAR2(64) PRIMARY KEY," +
            "CONSTRAINT USER_ID_FK FOREIGN KEY(USER_ID) REFERENCES USER_OES(USER_ID) ON DELETE  CASCADE " +
            ")";
    private static String Courses="CREATE TABLE C##OES.COURSES" +
            "(" +
            "COURSE_ID VARCHAR2(64) PRIMARY KEY," +
            "COURSE_NAME VARCHAR2(64)," +
            "USER_ID VARCHAR2(64)," +
            "CONSTRAINT COURSES_USER_ID_FK FOREIGN KEY(USER_ID) REFERENCES USER_OES(USER_ID) ON DELETE CASCADE\n" +
            ")";
    private static String studentTakes="CREATE TABLE C##OES.STUDENT_TAKES" +
            "(" +
            "USER_ID VARCHAR2(64)," +
            "COURSE_ID VARCHAR2(64)," +
            "CONSTRAINT STUDENT_TAKES_PK PRIMARY KEY(USER_ID,COURSE_ID)," +
            "CONSTRAINT ST_UID_FK FOREIGN KEY(USER_ID) REFERENCES USER_OES(USER_ID) ON DELETE CASCADE," +
            "CONSTRAINT ST_CID_FK FOREIGN KEY(COURSE_ID) REFERENCES COURSES(COURSE_ID) ON DELETE CASCADE" +
            ")";
    private static String messageCreate="CREATE TABLE C##OES.MESSAGES(" +
            "USER_ID VARCHAR2(64) NOT NULL," +
            "COURSE_ID VARCHAR2(64) NOT NULL," +
            "MESSAGE VARCHAR2(512) NOT NULL," +
            "MESSAGE_ID NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1," +
            "SENT_TIME DATE DEFAULT TO_DATE('0001-01-01:00-00-00','YYYY-MM-DD:HH24-MI-SS') NOT NULL," +
            "PRIMARY KEY(MESSAGE_ID)," +
            "CONSTRAINT MSG_UID_FK FOREIGN KEY(USER_ID) REFERENCES C##OES.USER_OES(USER_ID) ON DELETE CASCADE," +
            "CONSTRAINT MSG_CID_FK FOREIGN KEY(COURSE_ID) REFERENCES C##OES.COURSES(COURSE_ID) ON DELETE CASCADE" +
            ")";
    public static void create()
    {

        try {
            jdbcTemplate.execute(USER_OES);
        }catch (Exception e)
        { }
        try {
            jdbcTemplate.execute(ActiveUserInfo);
        }catch (Exception e)
        {}
        try {
            jdbcTemplate.execute(Courses);
        }catch (Exception e)
        {}
        try {
            jdbcTemplate.execute(studentTakes);
        }catch (Exception e)
        {}
        try{
            jdbcTemplate.execute(messageCreate);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
