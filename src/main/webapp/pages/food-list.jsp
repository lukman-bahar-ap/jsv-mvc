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
<head><title>Food List</title></head>
<body>
    <h2>Daftar Makanan</h2>
    <a href="home">Dashboard</a>

    <!-- Tombol Tambah hanya untuk admin -->
    <c:if test="${isAdmin}">
        <a href="food?action=new">Tambah Makanan</a>
    </c:if>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nama</th>
            <th>Harga</th>
            <c:if test="${isAdmin}"><th>Aksi</th></c:if>
        </tr>
        <c:forEach items="${list}" var="f">
            <tr>
                <td>${f.id}</td>
                <td>${f.name}</td>
                <td>${f.price}</td>
                <td>
                    <c:if test="${isAdmin}">
                        <a href="food?action=edit&id=${f.id}">Edit</a> |
                        <a href="food?action=delete&id=${f.id}" onclick="return confirm('Yakin hapus?')">Hapus</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
