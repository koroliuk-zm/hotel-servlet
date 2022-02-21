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
<title><fmt:message key="addRoom.title" /></title>
</head>
<body>
<jsp:include page="/admin_navbar.jsp"></jsp:include>
	<br>
	<br>
		<div class="container">
   	 <div class="row" >
        <div class="col-lg-4 col-md-6 col-sm-6 container justify-content-center card">
		<h2 class="text-center">
			<fmt:message key="addRoom.header" />
		</h2>
		<br>
		<br>
		<div class="card-body">
		<form action="main?action=addRoom" method="post">
			<div class="form-group">
			<input class="form-control" type="text" name="number" value="${room.number}" placeholder="<fmt:message key="rooms.form.main.number" />">
					<label style="color: red">${errors.number}</label>
					</div>
				<div class="form-group">
					<input class="form-control" type="text" name="seatsAmount" value="${room.seatsAmount}" placeholder="<fmt:message key="rooms.form.main.seatsAmount" />">
					<label style="color: red">${errors.seatsAmount}</label>
				</div>
				<div class="form-group">		
					<input class="form-control" type="text" name="perdayCost" value="${room.perdayCost}" placeholder="<fmt:message key="rooms.form.main.perdayCost" />">
					<label style="color: red">${errors.perdayCost}</label>
				</div>
				<div class="form-group">	
					<select class="form-select" name="roomStatusId">
							<c:set var="curRsId" value="${room.roomStatus.id}" />
							<c:forEach var="rs" items="${roomStatuses}">
								<option value="${rs.id}" <c:set var="roomStatusId" value="${rs.id}"/>
									<c:if test="${curRsId eq roomStatusId}">
						selected
					</c:if>>${rs.status}</option>
							</c:forEach>
					</select>
				</div>
				<br>
				<div class="form-group">
					<select class="form-select" name="roomTypeId">
							<c:set var="curRtId" value="${room.roomType.id}" />
							<c:forEach var="rt" items="${roomTypes}">
								<option value="${rt.id}" <c:set var="roomTypeId" value="${rt.id}"/>
									<c:if test="${curRtId eq roomTypeId}">
						selected
					</c:if>>${rt.type}</option>
							</c:forEach>
					</select>
				</div>
				<br>
				<div class="form-group">
					<input class="form-control" type="text" name="description" value="${room.description}" placeholder="<fmt:message key="rooms.form.main.description" />">
					<label style="color: red">${errors.description}</label>
				</div>
			<div class="form-group" style="text-align:center"> <button type="submit" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="c.button.submit"/>">
                            Submit
                        </button>
                        </div>
		</form>
	</div>
	</div>
	</div>
	</div>
</body>
</body>
</html>