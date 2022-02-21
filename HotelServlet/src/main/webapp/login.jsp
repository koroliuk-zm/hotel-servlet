<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<style>
</style>
<meta charset="UTF-8">
<title><fmt:message key="login.title" /></title>
</head>
<body>
<jsp:include page="/main_navbar.jsp"></jsp:include>
	<br>
	<br>
	<div class="container">
    <div class="row" >
        <div class="col-lg-4 col-md-5 col-sm-5 container justify-content-center card">
            <h2 class="text-center"> Please, login</h2>
            <div class="card-body">
			<form action="main?action=login" method="post">
				<div class="form-group">
					<fmt:message key="login.form.login" />
					<input class="form-control" type="text" name="login" value="${login}" <fmt:message key="login.placeholder.login"/> required autofocus ">
				</div>
				<div class="form-group">
					<fmt:message key="login.form.password" />
					<input class="form-control" type="password" name="password" <fmt:message key="login.placeholder.password"/> required autofocus ">
				</div>
			<label style="color: red">${errorLoginMessage}</label><br>
			<div class="form-group" style="text-align:center"> <button type="submit" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="login.form.button"/>">
                            Log in
                        </button>
				</div>
		</form>
	</div>
	</div>
	</div>
	</div>
</body>
</html>