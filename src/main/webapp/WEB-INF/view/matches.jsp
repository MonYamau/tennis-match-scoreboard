<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Законченные матчи</title>
</head>
<body>
<%@include file="fragment/header.jsp" %>
<main>
    <h1>Список законченных матчей</h1>
    <form action="/matches" method="GET">
        <input type="text" name="filter_by_player_name" placeholder="Поиск по имени...">
        <button type="submit">искать</button>
    </form>
    <table>
        <thead>
        <tr>
            <th>имя первого игрока</th>
            <th>имя второго игрока</th>
            <th>имя победителя матча</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${matchPage.matches}" var="match">
            <tr>
                <td>${match.firstPlayerName}</td>
                <td>${match.secondPlayerName}</td>
                <td>${match.winnerName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:choose>
        <c:when test="${matchPage.currentPage <= 4 and matchPage.pageCount - matchPage.currentPage <= 4}">
            <c:set var="startPage" value="1"/>
            <c:set var="endPage" value="${matchPage.pageCount}"/>
        </c:when>
        <c:when test="${matchPage.currentPage <= 4}">
            <c:set var="startPage" value="1"/>
            <c:set var="endPage" value="${matchPage.currentPage + 3}"/>
        </c:when>
        <c:when test="${matchPage.pageCount - matchPage.currentPage <= 4}">
            <c:set var="startPage" value="${matchPage.currentPage - 3}"/>
            <c:set var="endPage" value="${matchPage.pageCount}"/>
        </c:when>
        <c:otherwise>
            <c:set var="startPage" value="${matchPage.currentPage - 3}"/>
            <c:set var="endPage" value="${matchPage.currentPage + 3}"/>
        </c:otherwise>
    </c:choose>

    <c:forEach begin="${startPage}" end="${endPage}" var="page">
        <c:choose>
            <c:when test="${page == matchPage.currentPage}">
                <p>${page}</p>
            </c:when>
            <c:otherwise>
                <a href="/matches?page=${page}&filter_by_player_name=${matchPage.namePattern}">${page}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</main>
<%@include file="fragment/error-block.jsp" %>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
