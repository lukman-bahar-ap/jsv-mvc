<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
    boolean isAdmin = "admin".equals(user.getRole().getName());
    request.setAttribute("isAdmin", isAdmin); // supaya bisa dipakai di JSTL
%>

<html>
<head><title>User List</title></head>
<body>
    <h2>Daftar User</h2>
    <a href="home">Dashboard</a>

    <!-- Tombol Tambah hanya untuk admin -->
    <c:if test="${isAdmin}">
        <a href="user?action=new">Tambah User</a>
            <table border="1">
            <tr>
                <th>ID</th>
                <th>Nama</th>
                <th>Role</th>
                <th>Aksi</th>
            </tr>
            <c:forEach items="${list}" var="f">
                <tr>
                    <td>${f.id}</td>
                    <td>${f.username}</td>
                    <td>${f.role.name}</td>
                    <td>
                        <a href="user?action=edit&id=${f.id}">Edit</a> |
                        <a href="user?action=delete&id=${f.id}" onclick="return confirm('Yakin hapus?')">Hapus</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
