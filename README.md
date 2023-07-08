# social-login

#Welcome to social-login Authentication
#Procedure to Run this application

Go to the src/main/java -->  com.vikas.sociallogin -- > open SocialLoginApplication.java
Right click on that and do Run as Java Application.
 
Once server is get start Pls. try to access this application with below address : 

   http://localhost:8080/login
   if your want to change is server port then please add this below command in application.yaml available src/main/resources
 spring
   server
     port: <your desire port>


#####################################
Files
#####################################
1. open application.yaml location --> src/main/resources/
2. change your database name, username and password with your’s

    password: your-password
    url: jdbc:mysql://localhost:3306/your-database-name
    username: your-username


3. do the change in smtp server with your’s account details in application.yaml
 
    host: smtp.gmail.com // this is valid for gmail. if you are using some other smtp server then changes accordingly 
    password: your-password
    port: 587
    properties:
      mail:
        smtp:
          auth: true
          connectiontimeout: 5000
          starttls:
            enable: true
          timeout: 5000
          writetimeout: 5000
    username: your-username


4. change google client id and client secrets with your’s
    
       google:
            clientId: -- your google client id --
            clientSecret: -- your google client secrets --

5. change facebook client id and client secrets with your’s

         facebook:
            clientId: -- your facebook client id --
            clientSecret: -- your facebook client secrets --
			
			
5. change github client id and client secrets with your’s

         github:
            clientId: -- your facebook client id --
            clientSecret: -- your facebook client secrets --

7. In fecture inhancement add other social media account in the application.yaml file

8. Ready to go.


