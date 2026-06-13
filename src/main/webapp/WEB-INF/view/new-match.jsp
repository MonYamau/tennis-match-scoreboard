<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация нового матча</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <h1>Форма для регистрации</h1>
    <form action="new-match" METHOD="POST">
        <input type="text" id="firstPlayer" name="firstPlayer" required><label for="firstPlayer">Имя первого
        игрока</label>
        <input type="text" id="secondPlayer" name="secondPlayer" required><label for="secondPlayer">Имя второго
        игрока</label>
        <button type="submit">начать</button>
    </form>
</main>
<%@include file="fragment/error-block.jsp" %>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
