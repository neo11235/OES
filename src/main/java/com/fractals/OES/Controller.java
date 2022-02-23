package com.fractals.OES;

import com.fractals.OES.Classes.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Service service;
    private String failed="failed";
    private String success="success";
    @RequestMapping(method = RequestMethod.POST,value="/login")
    public LoginResponse login(@RequestBody LoginData loginData)
    {
        User user=null;
        try{
            if((user=service.getUser(loginData.getEmail(),loginData.getPassword()))==null)
                return new LoginResponse("failed",null);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        LoginResponse response=new LoginResponse("success",null);
        try{
            response.setToken(service.getToken());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        ActiveUserInfo activeUserInfo=new ActiveUserInfo(user.getUserId(),response.getToken());
        try{
            service.insertNewActiveUser(activeUserInfo);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        return response;
    }
    @RequestMapping(method = RequestMethod.POST,value = "/signup")
    public LoginResponse signup(@RequestBody User user)
    {
        try {
            user.setUserId(service.getUniqueId());
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        try
        {
            service.insertNewUser(user);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        LoginResponse res=new LoginResponse("success",null);
        try{
            res.setToken(service.getToken());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        ActiveUserInfo activeUserInfo=new ActiveUserInfo(user.getUserId(),res.getToken());
        try{
            service.insertNewActiveUser(activeUserInfo);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new LoginResponse("failed",null);
        }
        return res;
    }

    @RequestMapping("/logout/{token}")
    public Response logout(@PathVariable("token") String token)
    {
        try{
            service.logout(token);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Response("ok",null);
    }
    @RequestMapping(method=RequestMethod.POST,value="/createNewCourse/{token}")
    public Response createNewCourse(@PathVariable("token") String token,@RequestBody Course course)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"No user found");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        if(!user.getRole().equals("teacher"))
            return new Response(failed,"User dont have the privilege");
        course.setUserId(user.getUserId());
        try
        {
            course.setCourseId(service.getNewCourseId(course));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        try
        {
            service.insertNewCourse(course);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,"sql exception");
        }
        return new Response(success,null);
    }

    @RequestMapping(method = RequestMethod.POST,value="/joinCourse/{token}")
    public Response joinNewCourse(@PathVariable("token") String token,@RequestBody Course course)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"Could not find user");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        if(!user.getRole().equals("student"))
            return new Response(failed,"User not a student and cant join a course");
        try {
            if(service.getCourseById(course.getCourseId())==null)
                return new Response(failed,"No such course id exist");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        try{
            service.insertStudentTakes(new StudentTakes(user.getUserId(),course.getCourseId()));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        return new Response(success,null);
    }

    @RequestMapping("/home/{token}")
    public List<Course> getAllCourse(@PathVariable("token") String token)//user must already be verified before calling this otherwise it returns null
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)//shouldn't be true,just for extra safety
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        List<Course> courses=null;
        try
        {
            if(user.getRole().equals("teacher"))
            {
                courses=service.getCourseTakenByTeacher(user);
            }
            else
            {
                courses=service.getCourseTakenByStudent(user);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return  courses;
    }

    @RequestMapping(method = RequestMethod.POST,value="/sendMessage/{token}")
    public Response sendMessage(@PathVariable("token") String token,@RequestBody Message message)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"Cant find the user");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        message.setUserId(user.getUserId());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new Date(millis);
        message.setSentTime(date);
        try{
            service.insertNewMessage(message);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        return new Response(success,null);
    }

    @RequestMapping(method = RequestMethod.GET,value="/getMessage/{token}/{courseId}/{numberOfMessage}")
    public List<ReturnMessage> getMessage(@PathVariable("token") String token,@PathVariable("courseId") String courseId,@PathVariable("numberOfMessage") int numberOfMessage)
    {
        User user=null;
        try
        {
            user=service.getUserByToken(token);
            if(user==null)
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        List<ReturnMessage> messages=null;
        try{
            messages=service.getMessage(courseId,numberOfMessage);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return messages;
    }
    @RequestMapping(method = RequestMethod.POST,value="/createQuestion/{token}")
    public Response createQuestion(@PathVariable("token") String token,@RequestBody Question question)
    {
        User user=null;
        try
        {
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"cant find user");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        if(!user.getRole().equals("teacher"))
            return new Response(failed,"user dont have the privilege");
        question.setUserId(user.getUserId());
        try{
            question.setQuestionId(service.getNewQuestionId());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        try{
            service.insertNewQuestion(question);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        return new Response(success,null);
    }

    @RequestMapping(method = RequestMethod.GET,value="/getAllQuestions/{token}")
    public List<Question> getAllQuestion(@PathVariable("token") String token)
    {
        User user=null;
        try
        {
            user=service.getUserByToken(token);
            if(user==null)
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();;
            return null;
        }
        if(!user.getRole().equals("teacher"))
            return null;
        try{
            return service.getAllQuestion(user);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(method = RequestMethod.POST,value="/createExam/{token}")
    public Response createExam(@PathVariable("token") String token,@RequestBody ExamData examData)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"Cant find user");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }

        try {
            if(!user.getRole().equals("teacher")||!service.checkCourseExist(user.getUserId(),examData.getCourseId()))
                return new Response(failed,"User dont have the privilege");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        Exam exam=new Exam(null,examData.getStartTime(),examData.getEndTime(),examData.getCourseId(),examData.getExamName());
        try{
            exam.setExamId(service.getNewExamId());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        try{
            service.insertNewExam(exam,examData);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        return new Response(success,null);
    }

    @RequestMapping("/test")
    public AnswerScript Test()
    {
        List<Answer> answers=new ArrayList<>();
        answers.add(new Answer("qid1",1));
        answers.add(new Answer("qid1",2));
        answers.add(new Answer("qid1",3));
        answers.add(new Answer("qid1",4));
        answers.add(new Answer("qid1",5));
        AnswerScript answerScript=new AnswerScript("examid","userId",answers);
        return answerScript;
    }

    @RequestMapping(method = RequestMethod.GET,value="/getNotifications/{token}/{courseId}/{numberOfNotification}")
    public List<Notification> getNotification(@PathVariable("token") String token,
                                              @PathVariable("courseId") String courseId,
                                              @PathVariable("numberOfNotification") Integer numberOfNotification)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        try{
            if(!user.getRole().equals("student")||!service.checkIfStudentTakes(user.getUserId(),courseId))
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        try{
            return service.getNotification(user.getUserId(),courseId,numberOfNotification);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(method =RequestMethod.PUT,value = "/markAsRead/{token}/{notificationId}")
    public Response markAsRead(@PathVariable("token") String token,@PathVariable("notificationId") Integer notificationId)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"Cant find user");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        Notification notification=null;
        try {
            notification=service.getNotificationById(notificationId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        if(!notification.getUserId().equals(user.getUserId()))
            return new Response(failed,"Dont have the privilege");
        try{
            service.updateNotificationReadStatus(notificationId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        return new Response(success,null);
    }

    @RequestMapping(method = RequestMethod.GET,value="/getAllExam/{token}/{courseId}")
    public List<Exam> getAllExam(@PathVariable("token") String token, @PathVariable("courseId") String courseId)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        try{
            return service.getAllExams(courseId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="/getQuestionPaper/{token}/{examId}")
    public List<Question> getQuestionPaper(@PathVariable("token")String token,@PathVariable("examId")String examId)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        Exam exam=null;
        try
        {
            exam=service.getExamById(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        long millis=System.currentTimeMillis();
        java.util.Date curTime=new Date(millis);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        java.util.Date startTime;
        try {
            startTime = simpleDateFormat.parse(exam.getStartTime());
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        if(!user.getRole().equals("teacher")&&!startTime.before(curTime))
            return null;
        try{
            return service.getQuestionPaperByExamId(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="submitExamScript/{token}/{examId}")
    public Response submitAnswerScript(@PathVariable("token") String token,@PathVariable("examId") String examId,@RequestBody AnswerScript script)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return new Response(failed,"Cant find user");
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        Exam exam=null;
        try
        {
            exam=service.getExamById(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,"No such exam");
        }
        long millis=System.currentTimeMillis();
        java.util.Date curTime=new Date(millis);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        java.util.Date startTime,endTime;
        try {
            startTime = simpleDateFormat.parse(exam.getStartTime());
            endTime = simpleDateFormat.parse(exam.getEndTime());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,"Date parse error");
        }
        if(curTime.before(startTime)||endTime.before(curTime))
            return new Response(failed,"Cant submit outside of exam window");
        if(script.getAnswers()==null)
            return new Response(failed,"No answer was passed");
        if(!user.getRole().equals("student"))
            return new Response(failed,"User dont have the privilege");
        script.setUserId(user.getUserId());
        script.setExamId(examId);
        //user validated
        Result result=new Result();
        result.setExamId(examId);
        result.setUserId(user.getUserId());
        try{
            result.setScore(service.judgeScript(script));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        String answer=new Gson().toJson(script.getAnswers());

        DbAnswerScript dbAnswerScript=new DbAnswerScript(examId,user.getUserId(),answer);
        try{
            service.insertNewAnswerScript(dbAnswerScript);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        try{
            service.insertNewResult(result);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Response(failed,e.getMessage());
        }
        return new Response(success,null);
    }
    @RequestMapping(method = RequestMethod.GET, value="getTotalMark/{token}/{examId}")
    public Integer getTotalMarks(@PathVariable("token")String token,@PathVariable("examId")String examId)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return -1;
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        try{
            return service.getTotalMarks(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="/getResult/{token}/{examId}")
    public Integer getResult(@PathVariable("token") String token,@PathVariable("examId") String examId)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return -1;
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        Exam exam=null;
        try
        {
            exam=service.getExamById(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        long millis=System.currentTimeMillis();
        java.util.Date curTime=new Date(millis);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        java.util.Date endTime;
        try {
            endTime = simpleDateFormat.parse(exam.getEndTime());
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        if(curTime.before(endTime))
            return -1;
        try{
            return service.getResult(user.getUserId(),examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="/getAllResult/{token}/{examId}")
    public List<FrResult> getAllResult(@PathVariable("token") String token,@PathVariable("examId") String examId)
    {
        User user=null;
        try{
            user=service.getUserByToken(token);
            if(user==null)
                return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        Exam exam=null;
        try
        {
            exam=service.getExamById(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        long millis=System.currentTimeMillis();
        java.util.Date curTime=new Date(millis);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        java.util.Date endTime;
        try {
            endTime = simpleDateFormat.parse(exam.getEndTime());
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        if(user.getRole().equals("student")&&curTime.before(endTime))
            return null;
        try
        {
            return service.getAllResult(examId);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
