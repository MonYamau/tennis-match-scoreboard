<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<%@include file="WEB-INF/view/fragment/header.jsp" %>
<main>
    <h1>Добро пожаловать!</h1>
    <div class="home-buttons">
        <form action="${pageContext.request.contextPath}/new-match" method="GET">
            <button type="submit">Начать новый матч</button>
        </form>
        <form action="${pageContext.request.contextPath}/matches" method="GET">
            <button type="submit">Просмотреть завершённые матчи</button>
        </form>
    </div>
</main>
<%@include file="WEB-INF/view/fragment/footer.jsp" %>
</body>
</html>