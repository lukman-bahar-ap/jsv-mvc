<h1>SIMPLE JSV MVC CRUD WITH USER PERMISSIONS</h1>

<h3>GUIDE:</h3>

1. Don't forget create database first, or use this mysql dump file : 
`dump-jsvmvc.sql`

2. Set your DB authentification (URL, password and username) in file src\main\java\com\example\util\DBUtil.java
```
    private static final String URL = "jdbc:mysql://localhost:3306/jsvmvc";
    private static final String USER = "<your-username-here>";
    private static final String PASSWORD = "<your-password-here>";
```

3. RUN  CLI : 
`mvn clean package`

4. and then copy file extiontion .war to your apache tomcat path <br>
`cp <your-path->\target\jsp-mvc.war "<your-path>\tomcat\webapps\jsp-mvc.war"`

5. Run apache tomcat

<br>
<br>
<hr>

<h3>Requirements:</h3>
Minimal Apache Tomcat/11.0.8<br>
Minimal Mysql 5.2<br>
java 24 2025-03-18<br>
Java(TM) SE Runtime Environment (build 24+36-3646)
Java HotSpot(TM) 64-Bit Server VM (build 24+36-3646, mixed mode, sharing)

--------------------------------
