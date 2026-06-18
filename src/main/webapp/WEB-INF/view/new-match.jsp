<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Новый матч</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <h1>Форма для регистрации</h1>
    <%@include file="fragment/error-block.jsp" %>
    <form class="match-form" action="${pageContext.request.contextPath}/new-match" METHOD="POST">
        <label for="firstPlayer">Имя первого игрока</label>
        <input type="text" id="firstPlayer" name="firstPlayer" value="${param.firstPlayer}" required>
        <label for="secondPlayer">Имя второго игрока</label>
        <input type="text" id="secondPlayer" name="secondPlayer" value="${param.secondPlayer}" required>
        <button type="submit">начать</button>
    </form>
</main>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
