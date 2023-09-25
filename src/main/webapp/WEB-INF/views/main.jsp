<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="sidebar.jsp"%>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <script src="script.js"></script>
</head>
<body>
<div class="mw-100 container">
    <div class="main-text">
        ${title}
        <button type="button" class="visually-hidden"
                <c:if test="${login eq '로그인'}"> onclick="location.href='login.do'"</c:if>
                <c:if test="${login eq '로그아웃'}"> onclick="location.href='logout.do'"</c:if>>
            ${login}
        </button>
    </div>
    <div class="calender"> Item1 </div>
    <div class="project_list position-relative" onclick="location.href='/project/list.do'">
        <div>진행 중인 프로젝트</div>
        <a href='/project/insert.do' class="btn btn-outline-dark position-absolute top-50 start-50">프로젝트 추가</a>
    </div>
    <div class="issue">
        <c:forEach items="${issueList}" var="issue">
<%--            <c:if test="${issue.status ne 해결}">--%>
<%--                <div class="item card-body">--%>
                    <div class="text-bg-light">
                        <a href="/issue/detail.do?issueId=${issue.issueId}" class="text-reset text-decoration-none">
                            <div class="">${issue.title}</div>
                            <div class="">
                                <small class="text-body-secondary">${issue.date}<br>${issue.name}</small>
                            </div>
                        </a>
                    </div>
<%--                </div>--%>
<%--            </c:if>--%>
        </c:forEach>
    </div>
    <div class="task">
        <div class="card mt-3">
            <div class="card-body">
                <h5 class="card-title">달력</h5>
                <%@include file="calendar.jsp"%>
            </div>
        </div>
    </div>
</div>
</body>
</html>