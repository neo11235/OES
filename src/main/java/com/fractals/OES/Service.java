package com.fractals.OES;

import com.fractals.OES.Classes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
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

    public void insertNewMessage(Message message) throws  Exception{
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        String sql="INSERT  INTO "+databaseName+
                ".MESSAGES (USER_ID,COURSE_ID,MESSAGE,SENT_TIME) VALUES('" +
                message.getUserId()+"','"+
                message.getCourseId()+"','"+
                message.getMessage()+"',"+
                "TO_DATE('"+format.format(message.getSentTime())+"','YYYY-MM-DD:HH24-MI-SS'))";
        jdbcTemplate.execute(sql);
    }

    public List<ReturnMessage> getMessage(String courseId, Integer numberOfMessage) throws Exception{
        String sql="SELECT (UO.FIRST_NAME||' '||LAST_NAME) AS USER_NAME,M.MESSAGE AS MESSAGE,TO_CHAR(M.SENT_TIME,'YYYY-MM-DD:HH24-MI-SS') AS SENT_TIME " +
                    "FROM "+databaseName+".MESSAGES M JOIN "+databaseName+".USER_OES UO " +
                "ON (M.USER_ID=UO.USER_ID) WHERE M.COURSE_ID='"+courseId+"' " +
                "ORDER BY M.SENT_TIME DESC FETCH  NEXT "+numberOfMessage.toString()+
                " ROWS ONLY ";
        List<ReturnMessage> messages=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ReturnMessage.class));
        return messages;
    }

    public String getNewQuestionId() throws Exception {
        for(int i=0;i<10;++i)
        {
            String id=UUID.randomUUID().toString();
            String sql="SELECT * FROM "+databaseName+".QUESTIONS WHERE QUESTION_ID='"+id+"'";
            List<Question> result=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Question.class));
            if(result.isEmpty())
                return id;
        }
        throw new Exception("Failed to generate new Id");
    }

    public void insertNewQuestion(Question question) throws Exception{
        String sql="INSERT INTO "+databaseName+".QUESTIONS VALUES"+question.toString();
        jdbcTemplate.execute(sql);
    }

    public List<Question> getAllQuestion(User user)throws Exception {
        String sql="SELECT * FROM "+databaseName+".QUESTIONS WHERE USER_ID='"+user.getUserId()+"'";
        List<Question> result=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Question.class));
        return result;
    }

    public boolean checkCourseExist(String userId, String courseId) throws Exception{
        String sql="SELECT * FROM "+databaseName+".COURSES WHERE COURSE_ID='"+courseId+
                "' AND USER_ID='"+userId+"'";
        List<Course> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Course.class));
        return !res.isEmpty();
    }

    public String getNewExamId() throws Exception{
        for(int i=0;i<10;++i)
        {
            String id=UUID.randomUUID().toString();
            String sql="SELECT * FROM "+databaseName+".EXAMS WHERE EXAM_ID='"+id+"'";
            List<Exam> exams=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Exam.class));
            if(exams.isEmpty())
                return id;
        }
        throw new Exception("Failed to generate new exam id");
    }
    public void insertNewExam(Exam exam,ExamData examData) throws Exception{
        String sql="INSERT INTO "+databaseName+".EXAMS " +
                "VALUES ('" +exam.getExamId()+"',TO_DATE('"+exam.getStartTime()+"','YYYY-MM-DD:HH24-MI-SS')"+
                ",TO_DATE('"+exam.getEndTime()+"','YYYY-MM-DD:HH24-MI-SS'),'"+exam.getCourseId()+"','"
                +exam.getExamName()+"')";
        jdbcTemplate.execute(sql);
        List<String> questions=examData.getQuestionIds();
        for(String qid:questions)
        {
            String sql2="INSERT INTO "+databaseName+".QUESTION_PAPER VALUES('"
                    +exam.getExamId()+"','"+qid+"')";
            jdbcTemplate.execute(sql2);
        }
    }

    public boolean checkIfStudentTakes(String userId,String courseId) {
        String sql="SELECT * FROM "+databaseName+".STUDENT_TAKES WHERE USER_ID ='"+
                userId+"' AND COURSE_ID='"+courseId+"'";
        List<StudentTakes> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(StudentTakes.class));
        return !res.isEmpty();
    }

    public List<Notification> getNotification(String userId, String courseId, Integer numberOfNotification) {
        String sql="SELECT * FROM "+databaseName+".NOTIFICATIONS " +
                "WHERE USER_ID='"+userId+"' AND COURSE_ID = '"+courseId+
                "' ORDER BY NOTIFICATION_ID DESC " +
                "FETCH NEXT "+numberOfNotification.toString()+" ROWS ONLY";
        List<Notification> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Notification.class));
        return res;
    }

    public Notification getNotificationById(Integer notificationId) throws Exception{
        String sql="SELECT * FROM "+databaseName+".NOTIFICATIONS WHERE " +
                "NOTIFICATION_ID = "+notificationId.toString();
        List<Notification> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Notification.class));
        if(res.isEmpty())
            throw new Exception("No such notification found");
        return res.get(0);
    }

    public void updateNotificationReadStatus(Integer notificationId)throws Exception {
        String sql="UPDATE "+databaseName+".NOTIFICATIONS SET READ_STATUS = 1 WHERE " +
                "NOTIFICATION_ID = "+notificationId.toString();
        jdbcTemplate.execute(sql);
    }

    public List<Exam> getAllExams(String courseId) {
        String sql="SELECT EXAM_ID,TO_CHAR(START_TIME,'YYYY-MM-DD:HH24-MI-SS') START_TIME," +
                "TO_CHAR(END_TIME,'YYYY-MM-DD:HH24-MI-SS') END_TIME,COURSE_ID,EXAM_NAME " +
                "FROM "+databaseName+".EXAMS " +
                "WHERE COURSE_ID = '"+courseId+"'";
        List<Exam> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Exam.class));
        return res;
    }

    public Exam getExamById(String examId) throws Exception{
        String sql="SELECT EXAM_ID,TO_CHAR(START_TIME,'YYYY-MM-DD:HH24-MI-SS') START_TIME," +
                "TO_CHAR(END_TIME,'YYYY-MM-DD:HH24-MI-SS') END_TIME,COURSE_ID,EXAM_NAME " +
                "FROM "+databaseName+".EXAMS " +
                "WHERE EXAM_ID = '"+examId+"'";
        List<Exam> res=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Exam.class));
        if(res.isEmpty())
            throw new Exception("No such exam");
        return res.get(0);
    }

    public List<Question> getQuestionPaperByExamId(String examId) {
        String sql="SELECT Q.QUESTION_ID QUESTION_ID,Q.DESCRIPTION DESCRIPTION," +
                "Q.OPTION1 OPTION1,Q.OPTION2 OPTION2,Q.OPTION3 OPTION3," +
                "Q.OPTION4 OPTION4,Q.MARK MARK " +
                "FROM QUESTION_PAPER QP JOIN QUESTIONS Q " +
                "ON (QP.QUESTION_ID=Q.QUESTION_ID) " +
                "WHERE QP.EXAM_ID='"+examId+"'";
        List<Question> res= jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Question.class));

        return res;
    }
}
