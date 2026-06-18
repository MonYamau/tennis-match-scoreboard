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
    <%@include file="fragment/error-block.jsp" %>
    <c:choose>

        <c:when test="${matchPage != null}">
            <form class="match-list-filter" action="${pageContext.request.contextPath}/matches" method="GET">
                <div class="filter-group">
                <input type="text" name="filter_by_player_name" value="${matchPage.namePattern}" placeholder="Поиск по имени...">
                </div>
                <div class="filter-group">
                    <label for="limit-select">Количество матчей: </label>
                    <select id="limit-select" name="limit">
                        <option value="5" ${matchPage.limitValue == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${matchPage.limitValue == 10 ? 'selected' : ''}>10</option>
                        <option value="15" ${matchPage.limitValue == 15 ? 'selected' : ''}>15</option>
                        <option value="20" ${matchPage.limitValue == 20 ? 'selected' : ''}>20</option>
                    </select>
                </div>
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
                <c:when test="${matchPage.currentPage <= 4 and matchPage.pageCount - matchPage.currentPage < 4}">
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

            <div class="pagination">
                <c:forEach begin="${startPage}" end="${endPage}" var="page">
                    <c:choose>
                        <c:when test="${page == matchPage.currentPage}">
                            <span>${page}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/matches?page=${page}&filter_by_player_name=${matchPage.namePattern}&limit=${matchPage.limitValue}">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:when>

        <c:otherwise>
            <form class="matches-return-button" action="${pageContext.request.contextPath}/matches" method="GET">
                <button type="submit">Вернуться ко всему списку</button>
            </form>
        </c:otherwise>
    </c:choose>
</main>
<%@include file="fragment/footer.jsp" %>
</body>
</html>
