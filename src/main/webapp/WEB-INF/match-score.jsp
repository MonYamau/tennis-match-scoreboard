<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Текущий матч</title>
</head>
<body>
<h1>Счёт матча</h1>
<p>${match.firstPlayerName} VS ${match.secondPlayerName}</p>
<p>Счёт матча: ${match.currentMatch.firstPlayerScore}
    Счёт матча: ${match.currentMatch.secondPlayerScore}</p>
<p>Счёт сета: ${match.currentMatch.currentSet.firstPlayerScore}
    Счёт сета: ${match.currentMatch.currentSet.secondPlayerScore}</p>
<p>Счёт игры: ${match.currentMatch.currentSet.currentGame.firstPlayerScore}
    Счёт игры: ${match.currentMatch.currentSet.currentGame.secondPlayerScore}</p>
<form action="/match-score" method="POST">
    <input type="hidden" name="uuid" value=${match.uuid}>
    <input type="hidden" name="winnerId" value=${match.firstPlayerId}>
    <button type="submit">Игрок 1 выиграл очко</button>
</form>
<form action="/match-score" method="POST">
    <input type="hidden" name="uuid" value=${match.uuid}>
    <input type="hidden" name="winnerId" value=${match.secondPlayerId}>
    <button type="submit">Игрок 2 выиграл очко</button>
</form>
</body>
</html>
