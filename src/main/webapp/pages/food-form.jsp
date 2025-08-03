<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>Form Makanan</title></head>
    <body>
        <h2>Form Makanan</h2>
        <form action="food" method="post">
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="id" value="${food.id}">

            <label for="name">Nama:</label>
            <input type="text" id="name" name="name" value="${food.name}" required><br>

            <label for="price">Harga:</label>
            <input type="number" id="price" name="price" value="${food.price}" required><br>

            <input type="submit" value="Simpan">
            <a href="food">Kembali ke Daftar</a>
        </form>
</html>