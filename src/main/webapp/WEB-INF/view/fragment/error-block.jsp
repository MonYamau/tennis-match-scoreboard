<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errorMessage != null}">
    <div>
        <h3>Error ${errorCode}</h3>
        <p>${errorMessage}</p>
    </div>
</c:if>
