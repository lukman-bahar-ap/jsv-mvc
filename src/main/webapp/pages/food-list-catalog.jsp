<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Food List</title></head>
<body>
    <h2>Daftar Makanan</h2>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nama</th>
            <th>Harga</th>
        </tr>
        <c:forEach items="${list}" var="f">
            <tr>
                <td>${f.id}</td>
                <td>${f.name}</td>
                <td>${f.price}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
