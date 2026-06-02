<%--
  Created by IntelliJ IDEA.
  User: MonYamau
  Date: 01.06.2026
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация нового матча</title>
</head>
<body>
<h1>Форма для регистрации</h1>
<form action="new-match" METHOD="POST">
    <input type="text" name="firstPlayer" placeholder="Имя первого игрока" required>
    <input type="text" name="secondPlayer" placeholder="Имя второго игрока" required>
    <button type="submit">начать</button>
</form>
</body>
</html>
