<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<%@include file="WEB-INF/view/fragment/header.jsp"%>
<main>
    <h1>Добро пожаловать!</h1>
    <a href="new-match">Создать новый матч</a>
    <a href="matches">Просмотреть завершённые матчи</a>
</main>
<%@include file="WEB-INF/view/fragment/footer.jsp"%>
</body>
</html>