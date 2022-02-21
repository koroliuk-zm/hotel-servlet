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
<title><fmt:message key="processRequest.title" /></title>
</head>
<body>
	<jsp:include page="/waiter_navbar.jsp"></jsp:include>
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div
				class="col-lg-4 col-md-6 col-sm-6 container justify-content-center card">
				<h2 class="text-center">
					<fmt:message key="processReques.header" />
				</h2>
				<br>
				<br>
				<div class="card-body">
					<form action="main?action=processRequest" method="post">
					<input type="hidden" name="rId" value="${request.id}">
					<input type="hidden" name="uId" value="${user.id}">
						<div class="form-group">
							<fmt:message key="request.seatsNumber" />
							<input class="form-control" type="text"
								value="${request.seatsNumber}" readonly>
						</div>
						<div class="form-group">
							<fmt:message key="rooms.form.main.roomType" />
							<input class="form-control" type="text"
								value="${request.roomType.type}" readonly>
						</div>
						<div class="form-group">
							<fmt:message key="request.checkInDate" />
							<input class="form-control" type="date" name="checkInDate"
								value="${request.checkInDate}" readonly="readonly"> 
						</div>
						<div class="form-group">
							<fmt:message key="request.checkOutDate" />
							<input class="form-control" type="date" name="checkOutDate"
								value="${request.checkOutDate}" readonly="readonly"> 
						</div>
						<div class="form-group">
							<fmt:message key="order.roomNumber" />
							<input class="form-control" type="text" name="roomNumber"
								value="${order.room.number}"> <label style="color: red">${errors.number}</label>
						</div>
						<div class="form-group" style="text-align: center">
							<button type="submit" class="btn btn-lg btn-primary btn-block"
								value="<fmt:message key="c.button.submit"/>">Submit</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>