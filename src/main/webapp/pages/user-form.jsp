<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Form User</title>
</head>
<body>
    <h2>${formUser.id == 0 || formUser.id == null ? "Tambah" : "Edit"} User</h2>

    <form action="user" method="post">
        <input type="hidden" name="id" value="${formUser.id}" />

        <label>Username:</label><br>
        <input type="text" name="username" value="${formUser.username}" required /><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" value="${formUser.password}" required /><br><br>

        <label>Role:</label><br>
        <select name="role_id" required>
            <c:forEach var="role" items="${roleList}">
                <option value="${role.id}" ${role.id == formUser.role.id ? "selected" : ""}>
                    ${role.name}
                </option>
            </c:forEach>
        </select><br><br>

        <button type="submit">Simpan</button>
        <a href="user">Batal</a>
    </form>
</body>
</html>
