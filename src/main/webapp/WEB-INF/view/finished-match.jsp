<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Конец матча</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <c:choose>
        <c:when test="${match.winnerId == match.firstPlayerId}">
            <h1>Победа игрока ${match.firstPlayerName}!</h1>
        </c:when>
        <c:when test="${match.winnerId == match.secondPlayerId}">
            <h1>Победа игрока ${match.secondPlayerName}!</h1>
        </c:when>
    </c:choose>
    <table>
        <thead>
        <tr>
            <th>${match.firstPlayerName}</th>
            <th>${match.secondPlayerName}</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${match.currentMatch.firstPlayerScore}</td>
            <td>${match.currentMatch.secondPlayerScore}</td>
        </tr>
        </tbody>
    </table>
    <form action="/" method="GET">
        <button type="submit">Вернуться на главную страницу</button>
    </form>
</main>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
