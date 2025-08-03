<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.PermissionDAO" %>
<%@ page import="com.example.model.Role" %>
<%@ page import="com.example.model.Permission" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    PermissionDAO perm = new PermissionDAO();
    List<Role> roles = (List<Role>) request.getAttribute("roles");
    List<Permission> permissions = (List<Permission>) request.getAttribute("permissions");
%>
<html>
    <head><title>Admin: Role Permission Management</title></head>
    <body>
        <h2>Admin Role Managemen</h2>
        <a href="home">Dashboard</a>
        <table border="1">
        <tr>
            <th>Role</th>
            <% for (Permission p : permissions) { %>
            <th><%= p.getName() %></th>
            <% } %>
        </tr>
        <% for (Role r : roles) { %>
        <tr>
            <td><%= r.getName() %></td>
            <% for (Permission p : permissions) { 
                Boolean checked = perm.getRoleHasPermission(r.getId(), p.getId());
                %>
            <td>
            <form method="post" action="admin" style="display:inline;">
                <input type="hidden" name="roleId" value="<%= r.getId() %>" />
                <input type="hidden" name="permId" value="<%= p.getId() %>" />
                <input type="checkbox" name="enable" value="on"
                <%= (checked != null && checked) ? "checked" : "" %>
                onchange="this.form.submit();" />
            </form>
            </td>
            <% } %>
        </tr>
        <% } %>
        </table>
    </body>
</html>