<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if( user == null ){ response.sendRedirect("login"); return; }
%>
<html>
    <head>
        <title>Dashboard</title>
    </head>
    <body>
        <h1>Selamat datang di Dashboard, <%= user.getUsername() %>!</h1>
        <ul>
            <li><a href="food">Food List</a></li>
            <c:if test="${user.role.name == 'admin'}">
             <li><a href="admin">Admin menu</a></li>
             <li><a href="foodlist?action=new">Add Food</a></li>
            </c:if>
            <li><a href="logout">logout</a></li>
        </ul>
    </body>
</html>