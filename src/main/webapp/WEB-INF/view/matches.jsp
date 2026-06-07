<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Законченные матчи</title>
</head>
<body>
<h1>Список законченных матчей</h1>
<c:forEach items="${matchPage.matches}" var="match">
    <p>${match.firstPlayerName}</p>
    <p>${match.secondPlayerName}</p>
    <p>${match.winnerName}</p>
</c:forEach>
</body>
</html>
