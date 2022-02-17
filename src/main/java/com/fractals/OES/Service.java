package com.fractals.OES;

import com.fractals.OES.Classes.ActiveUserInfo;
import com.fractals.OES.Classes.Course;
import com.fractals.OES.Classes.StudentTakes;
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
    public User getUser(String email,String password)throws Exception
    {
        String sql="SELECT * FROM "+databaseName+".USER_OES WHERE EMAIL='"+email+"' AND PASSWORD='"+password+"'";
        List<User> res=jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
        if(res.isEmpty())
            return null;
        return res.get(0);
    }
    public User getUserById(String id) throws Exception
    {
        String sql="SELECT * FROM "+databaseName+".USER_OES WHERE USER_ID = '"+id+"'";
        List<User> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(User.class));
        if(res.isEmpty())
            return null;
        return res.get(0);
    }
    public User getUserByToken(String token) throws Exception
    {
        String sql="SELECT * FROM "+databaseName+"."+"ACTIVE_USER_INFO WHERE TOKEN='"+token+"'";
        List<ActiveUserInfo> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ActiveUserInfo.class));
        if(res.isEmpty())
            return null;
        return getUserById(res.get(0).getUserId());
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
    public String getNewCourseId(Course course) throws Exception
    {
        String sql1="SELECT * FROM "+databaseName+".COURSES WHERE USER_ID= '"+course.getUserId()+
                "' AND COURSE_NAME = '"+course.getCourseName()+"'";
        List<Course> res1=jdbcTemplate.query(sql1,BeanPropertyRowMapper.newInstance(Course.class));
        if(!res1.isEmpty())
            throw new Exception("Course already exist");
        for(int i=0;i<10;++i)
        {
            String id=UUID.randomUUID().toString();
            String sql2="SELECT * FROM "+databaseName+".COURSES WHERE COURSE_ID='"+id+"'";
            List<Course> res2=jdbcTemplate.query(sql2,BeanPropertyRowMapper.newInstance(Course.class));
            if(res2.isEmpty())
                return  id;
        }
        throw new Exception("Could not generate unique Course ID");
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

    public void insertNewCourse(Course course) throws  Exception{
        String sql="INSERT INTO "+databaseName+".COURSES VALUES"+course.toString();
        jdbcTemplate.execute(sql);
    }

    public Course getCourseById(String courseId) {
        String sql="SELECT * FROM "+ databaseName+".COURSES WHERE COURSE_ID='"+courseId+"'";
        List<Course> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Course.class));
        if(res.isEmpty())
            return null;
        return res.get(0);
    }

    public void insertStudentTakes(StudentTakes takes) throws Exception{
        String sqlCheck="SELECT * FROM "+databaseName+".STUDENT_TAKES WHERE USER_ID='"+takes.getUserId()+"' AND COURSE_ID='"+takes.getCourseId()+"'";
        List<StudentTakes> res=jdbcTemplate.query(sqlCheck,BeanPropertyRowMapper.newInstance(StudentTakes.class));
        if(!res.isEmpty())
            throw new Exception("Student already takes the course");
        String sql="INSERT INTO "+databaseName+".STUDENT_TAKES VALUES"+takes.toString();
        jdbcTemplate.execute(sql);
    }

    public List<Course> getCourseTakenByTeacher(User user) throws Exception{
        String sql="SELECT * FROM "+databaseName+".COURSES WHERE USER_ID='"+user.getUserId()+"'";
        List<Course> courseList=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Course.class));
        for(int i=0;i<courseList.size();++i)
        {
            courseList.get(i).setUserId(null);
        }
        return courseList;
    }
    public List<Course> getCourseTakenByStudent(User user) throws Exception {
        String sql="SELECT C.COURSE_ID AS COURSE_ID,C.COURSE_NAME AS COURSE_NAME,C.USER_ID AS USER_ID " +
                "FROM "+databaseName+".STUDENT_TAKES ST " +
                "JOIN "+databaseName+".COURSES C " +
                "ON (ST.COURSE_ID=C.COURSE_ID) " +
                "WHERE ST.USER_ID='"+user.getUserId()+"'";
        List<Course> courseList=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Course.class));
        for(int i=0;i<courseList.size();++i)
        {
            courseList.get(i).setUserId(null);
        }
        return courseList;
    }
}
