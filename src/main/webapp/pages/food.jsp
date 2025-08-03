<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Daftar Makanan</title>
</head>
<body>
    <h1>Menu Makanan Hari Ini</h1>
    <ul>
        <c:forEach var="item" items="${menu}">
            <li>${item}</li>
        </c:forEach>
    </ul>
</body>
</html>