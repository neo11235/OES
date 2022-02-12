package com.fractals.OES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CreateDatabase {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void createUserOES()
    {
        String sql="CREATE TABLE C##OES.USER_OES" +
                "(" +
                "USER_ID VARCHAR2(30) PRIMARY KEY," +
                "FIRST_NAME VARCHAR2(30) NOT NULL," +
                "LAST_NAME VARCHAR2(30) NOT NULL," +
                "EMAIL VARCHAR2(30) NOT NULL UNIQUE," +
                "PASSWORD VARCHAR2(40) NOT NULL," +
                "BIRTH_DATE DATE NOT NULL," +
                "ROLE VARCHAR2(10) NOT NULL," +
                "PHN_NUMBER VARCHAR2(15)" +
                ")";
        jdbcTemplate.execute(sql);
    }
}
