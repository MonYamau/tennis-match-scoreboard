<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Конец матча</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <div class="winner-banner">
        <div class="trophy-icon">🏆</div>
        <c:choose>
            <c:when test="${match.winnerId == match.firstPlayerId}">
                <h1>Победа игрока ${match.firstPlayerName}!</h1>
            </c:when>
            <c:when test="${match.winnerId == match.secondPlayerId}">
                <h1>Победа игрока ${match.secondPlayerName}!</h1>
            </c:when>
        </c:choose>
    </div>

    <table class="final-scoreboard">
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

    <div class="center-container">
        <form action="${pageContext.request.contextPath}/" method="GET">
            <button type="submit">Вернуться на главную страницу</button>
        </form>
    </div>
</main>
<%@include file="fragment/footer.jsp" %>
</body>
</html>