# OES
Backend for Online Exam system.
Api's: 
1. POST, url:'/login' Body:{userName,Password} Returns:{success or fail,token}
2. POST, url:'/signup' Body:{firstName,lastName,email,password,birthdate,role,phoneNumber} Return:{success or fail,token}
3. GET, url:'/logout/{token}' Return:{success or fail,token}
4. POST, url:‘/createNewCourse/{token}’ Body:{courseName},Returns:{success or fail,null}
5. POST url:‘/joinCourse/{token}’ Body:{courseId}  Returns:{success or fail,null}
6. GET  url:‘/home/{token}' Returns:List[{courseId,courseName}]
7. POST url:‘/sendMessage/{token}’ Body:{courseId,Message} Returns:{status,message}
8. GET url:‘/getMessage/{token}/{courseId}/{numberOfMessage} Returns:List[ReturnMessage.class]
9. POST url:‘/createQuestion/{token} {Question.class} Returns:{status,message}
10.GET url:‘/getAllQuestions/{token}’ Returns:List[Questions.class]
11.POST url:‘/createExam/{token}’ {ExamData.class} Returns:{status,message}
12. GET url:‘/getNotifications/{token}/{courseId}/{numberOfNotification}’ Returns:List[Notification.class]
13. PUT url:‘/markAsRead/{token}/{notificationId} Returns:{status,message}
14. GET url:‘/getAllExam/{token}/{courseId}’ Returns: List[Exam.class]
15. GET url:‘/getQuestionPaper/{token}/{examId} Returns: List[Question.class]
16. POST url:‘/submitExamScript/{token}/{examId} Body:{AnswerScript.class} Returns {status,message}
17. GET url:‘/getTotalMark/{token}/{examId} Returns:{Integer}
18. GET url:‘/getResult/{token}/{examId} Returns:{Integer}
19. Get url:‘/getAllResult/{token}/{examId} Returns:List[FrResult.class]
20. Get url:‘/getSolution/{token}/{examId} Returns: List[Question.class]
