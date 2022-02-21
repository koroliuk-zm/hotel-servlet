<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
	crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title><fmt:message key="admin.title" /></title>
</head>
<body>
	<jsp:include page="/admin_navbar.jsp"></jsp:include>
	<br>
	<br>
	<div class="container">
    <div class="row">
        <h1 class="text-center"> <fmt:message key="header.admin" /></h1>
    </div>
    <br>
    <div class="row">
        <div class="col-lg-3">
        <a class="btn btn-success mb-2" href="main?action=addUserLink"><fmt:message
								key="c.button.addUser" /></a>
        </div>
        </div>
		<table class="table table-success table-striped">
        <thead class="table-dark">
			<tr>
				<th><fmt:message key="c.login" /></th>
				<th><fmt:message key="c.name" /></th>
				<th><fmt:message key="c.surname" /></th>
				<th><fmt:message key="c.userRole" /></th>
				<th><fmt:message key="c.email" /></th>
				<th style="width:10%; text-align: right;"><fmt:message key="c.action.edit" /></th>
				<th style="width:10%; text-align: right;"><fmt:message key="c.action.delete" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td><c:out value="${user.login}" /></td>
					<td><c:out value="${user.name}" /></td>
					<td><c:out value="${user.surname}" /></td>
					<td><c:out value="${user.userRole.role}" /></td>
					<td><c:out value="${user.email}" /></td>
					<td style="width:10%; text-align: right;"><a class="btn btn-primary"
						href="main?action=updateUserPage&uId=${user.id}"><fmt:message
								key="c.button.edit" /></a></td>
					<td style="width:10%; text-align: right;"><a class="btn btn-danger"
						href="main?action=deleteUser&uId=${user.id}"><fmt:message
								key="c.button.delete" /></a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<p>
		${paginationNav}
		</p>
	</div>
</body>
</html>