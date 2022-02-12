package com.fractals.OES;

import com.fractals.OES.Classes.ActiveUserInfo;
import com.fractals.OES.Classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String databaseName="C##OES";
    public User checkUserExist(String email,String password)
    {
        String sql="SELECT * FROM "+databaseName+".USER_OES WHERE EMAIL='"+email+"' AND PASSWORD='"+password+"'";
        List<User> res=jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
        if(res.isEmpty())
            return null;
        return res.get(0);
    }
    public String getUniqueId() throws Exception{

        for(int i=0;i<10;++i)
        {
            String id= UUID.randomUUID().toString();
            String sql="SELECT * FROM "+databaseName+"."+"USER_OES WHERE USER_ID='"+id+"'";
            List<User> res=jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
            if(res.isEmpty())
                return id;
        }
        throw new Exception("Could not generate unique User ID");
    }

    public String getToken() throws Exception{
        for(int i=0;i<10;++i)
        {
            String token=UUID.randomUUID().toString();
            String sql="SELECT * FROM "+databaseName+"."+"ACTIVE_USER_INFO WHERE TOKEN='"+token+"'";
            List<ActiveUserInfo> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ActiveUserInfo.class));
            if(res.isEmpty())
                return  token;
        }
        throw new Exception("Could not generate unique token");
    }

    public void insertNewUser(User user) {
        String sql="INSERT INTO "+databaseName+".USER_OES "
                +"VALUES"+user.toString();
        jdbcTemplate.execute(sql);
    }
    public void insertNewActiveUser(ActiveUserInfo activeUserInfo)throws Exception
    {
        String sql="INSERT INTO "+databaseName+".ACTIVE_USER_INFO VALUES"+activeUserInfo.toString();
        jdbcTemplate.execute(sql);
    }

    public void logout(String token) {
        String sql="DELETE FROM "+databaseName+".ACTIVE_USER_INFO WHERE TOKEN='"+token+"'";
        jdbcTemplate.execute(sql);
    }
}
