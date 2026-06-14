<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Текущий матч</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <h1>Счёт матча</h1>
    <table>
        <thead>
        <tr>
            <th>имя игрока</th>
            <th>счёт матча</th>
            <th>счёт сета</th>
            <th>счёт гейма</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${match.firstPlayerName}</td>
            <td>${match.currentMatch.firstPlayerScore}</td>
            <td>${match.currentMatch.currentSet.firstPlayerScore}</td>
            <td>${match.currentMatch.currentSet.currentGame.firstPlayerScore}</td>
        </tr>
        <tr>
            <td>${match.secondPlayerName}</td>
            <td>${match.currentMatch.secondPlayerScore}</td>
            <td>${match.currentMatch.currentSet.secondPlayerScore}</td>
            <td>${match.currentMatch.currentSet.currentGame.secondPlayerScore}</td>
        </tr>
        </tbody>
    </table>
    <div class="match-controls">
        <form action="/match-score" method="POST">
            <input type="hidden" name="uuid" value="${match.uuid}">
            <input type="hidden" name="winnerId" value="${match.firstPlayerId}">
            <button type="submit">Игрок 1 выиграл очко</button>
        </form>
        <form action="/match-score" method="POST">
            <input type="hidden" name="uuid" value="${match.uuid}">
            <input type="hidden" name="winnerId" value="${match.secondPlayerId}">
            <button type="submit">Игрок 2 выиграл очко</button>
        </form>
    </div>
</main>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
