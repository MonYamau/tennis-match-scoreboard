<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ошибка</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <c:choose>
        <c:when test="${errorCode == 404}">
            <h1>Страница не найдена</h1>
            <p>Пожалуйста, перейдите по актуальному адресу или вернитесь на <a href="/">главную страницу</a></p>
        </c:when>
        <c:when test="${errorCode == 500}">
            <h1>Возникла ошибка на стороне сервера</h1>
            <p>Пожалуйста, повторите попытку позднее или вернитесь на <a href="/">главную страницу</a></p>
        </c:when>
        <c:otherwise>
            <h1>Неизвестная ошибка</h1>
            <p>Пожалуйста, вернитесь на <a href="/">главную страницу</a></p>
        </c:otherwise>
    </c:choose>
    <p>Возникла ошибка:</p>
    <p>${errorMessage}</p>
</main>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
