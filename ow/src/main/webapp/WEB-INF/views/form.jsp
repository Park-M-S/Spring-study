<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>데이터베이스 테스트</title>
</head>
<body>
    <h2>데이터베이스 테스트</h2>
    <form action="submit" method="post">
        이름: <input type="text" name="name"/><br/>
        나이: <input type="number" name="age"/><br/>
        <input type="submit" value="제출" />
    </form>

    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>
</body>
</html>
