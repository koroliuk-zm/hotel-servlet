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
<title><fmt:message key="addUser.title" /></title>
</head>
<body>
<jsp:include page="/admin_navbar.jsp"></jsp:include>
	<br>
	<br>
	<div class="container">
   	 <div class="row" >
        <div class="col-lg-4 col-md-6 col-sm-6 container justify-content-center card">
		<h2 class="text-center">
			<fmt:message key="addUser.header" />
		</h2>
		<br>
		<br>
		<div class="card-body">
		<form action="main?action=addUser" method="post">
				<div class="form-group">
					<input class="form-control" type="text" name="login" value="${user.login}" placeholder="<fmt:message key="c.login" />">
					<label style="color: red">${errors.login}</label>
				</div>
				<div class="form-group">
					<input class="form-control" type="text" name="name" value="${user.name}" placeholder="<fmt:message key="c.name" />">
					<label style="color: red">${errors.name}</label>
				</div>
				<div class="form-group">
					<input class="form-control" type="text" name="surname" value="${room.surname}" placeholder="<fmt:message key="c.surname" />">
					<label style="color: red">${errors.surname}</label>
				</div>
				<div class="form-group">
					<input class="form-control" type="password" name="password" placeholder="<fmt:message key="c.password" />">
					<label style="color: red">${errors.password}</label>
				</div>
				<div class="form-group">
					<input class="form-control" type="password" name="passwordConfirm" placeholder="<fmt:message key="c.passwordConfirm" />">
				</div>
				<br>
				<div class="form-group">
					<select class="form-select" name="roleId">
							<c:set var="curUrId" value="${user.userRole.id}" />
							<c:forEach var="ur" items="${userRoles}">
								<option value="${ur.id}" <c:set var="roleId" value="${ur.id}"/>
									<c:if test="${curUrId eq roleId}">
						selected
					</c:if>>${ur.role}</option>
							</c:forEach>
					</select>
				</div>
				<br>
				<div class="form-group">					
					<input class="form-control" type="text" name="email" value="${user.email}" placeholder="<fmt:message key="c.email" />">
					<label style="color: red">${errors.email}</label>
				</div>
				<div class="form-group">
					<select class="form-select" name="isEnable" >
							<option value="false">false</option>
							<option value="true">true</option>
					</select>
				</div>
			<br>
			<div class="form-group" style="text-align:center"> <button type="submit" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="c.button.submit"/>">
                            Submit
                        </button>
                        </div>
		</form>
	</div>
	</div>
	</div></div>
</body>
</body>
</html>