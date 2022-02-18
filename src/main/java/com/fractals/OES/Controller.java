package com.fractals.OES;

import com.fractals.OES.Classes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
}
