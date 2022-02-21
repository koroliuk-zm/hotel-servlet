<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tag" uri="customtags"%>
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
<title>${currentUser.name}${currentUser.surname}</title>
</head>
<body>
<jsp:include page="/waiter_navbar.jsp"></jsp:include>
	<br>
	<br>
	<div class="container">
    <div class="row">
        <h1 class="text-center"><c:if test="${enable}">
					<label style="color: red"><fmt:message
							key="privateCabinet.blocked" /></label>
				</c:if></h1>
    </div>
    <div class="row">
        <h1 class="text-center"><fmt:message key="privateCabinet.header" /></h1>
    </div>
		<br>
		<br>
		<table>
		<tbody>
			<tr>
				<th><fmt:message key="privateCabinet.table.login" /></th>
				<td>${currentUser.login}</td>
			</tr>
			<tr>
				<th><fmt:message key="privateCabinet.name" /></th>
				<td>${currentUser.name}</td>
			</tr>
			<tr>
				<th><fmt:message key="privateCabinet.surname" /></th>
				<td>${currentUser.surname}</td>
			</tr>
			</tbody>
		</table>
		<br> <br>
		<h3 class="text-center">
				<fmt:message key="waiterCabinet.requests" />
			</h3>
			<br>
			<table class="table table-success table-striped">
				<thead class="table-dark">
					<tr>
						<th><fmt:message key="privateCabinet.table.login" /></th>
						<th><fmt:message key="privateCabinet.table.orderDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckInDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckOutDate" /></th>
						<th><fmt:message key="waiterCabinet.table.seatsNumber" /></th>
						<th><fmt:message key="privateCabinet.table.roomType" /></th>
						<th style="width:10%; text-align: right;"><fmt:message key="waiterCabinet.button.process" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="request" items="${requests}">
						<tr>
							<td><c:out value="${request.user.login}" /></td>
							<td><tag:dateTime value="${request.requestDate}" /></td>
							<td><tag:dateTime value="${request.checkInDate}" /></td>
							<td><tag:dateTime value="${request.checkOutDate}" /></td>
							<td><c:out value="${request.seatsNumber}" /></td>
							<td><c:out value="${request.roomType.type}" /></td>
							<td class="roomEdit" style="width:10%; text-align: right;"><a class="btn btn-primary btn-sm"
						href="main?action=processRequestPage&rId=${request.id}"><fmt:message
								key="c.button.process" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br><br><br>
			<h3 class="text-center">
				<fmt:message key="waiterCabinet.freeRooms" />
			</h3>
			<br>
			<form action="main" method="get">
				<input type="hidden" name="action" value="waiterPage">
				<fmt:message key="rooms.form.sort.sortBy"/>
				<select name="sort">
					<c:forEach var="sortByItem" items="${sortBy}">
						<option value="${sortByItem.value}"
							<c:set var="cn" value="${sortByItem.value}"/>
							<c:if test="${cn eq sort}">
							selected
						</c:if>>${sortByItem.title}</option>
					</c:forEach>
				</select>
				<fmt:message key="rooms.form.sort.orderBy"/>
				<select name="order">
					<c:forEach var="orderByItem" items="${orderBy}">
						<option value="${orderByItem.value}"
							<c:set var="cn" value="${orderByItem.value}"/>
							<c:if test="${cn eq order}">
							selected
						</c:if>>${orderByItem.title}</option>
					</c:forEach>
				</select>
				<input class="btn btn-success mb-2" type="submit" value="<fmt:message key="rooms.form.sort.button"/>"><br><br>
				<fmt:message key="rooms.form.sort.FindBy"/>
				<input type="text" name="seatsAmount" value="${seatsAmount}" placeholder="<fmt:message key="rooms.form.find.findBySeatsAmount"/>">
				<input type="text" name="perdayCost" value="${perdayCost}" placeholder="<fmt:message key="rooms.form.main.perdayCost"/>">
				<input type="text" name="roomStatus" value="${roomStatus}" placeholder="<fmt:message key="rooms.form.main.roomStatus"/>">
				<input type="text" name="roomType" value="${roomType}" placeholder="<fmt:message key="rooms.form.main.roomType"/>">
				<input class="btn btn-success mb-2" type="submit" value="<fmt:message key="rooms.form.find.button"/>">
			</form>
		<br><br>
		<table class="table table-success table-striped">
        	<thead class="table-dark">
			<tr>
				<th><fmt:message key="rooms.form.main.number" /></th>
				<th><fmt:message key="rooms.form.main.seatsAmount" /></th>
				<th><fmt:message key="rooms.form.main.perdayCost" /></th>
				<th><fmt:message key="rooms.form.main.roomStatus" /></th>
				<th><fmt:message key="rooms.form.main.roomType" /></th>
				<th><fmt:message key="rooms.form.main.description" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="room" items="${freeRooms}">
				<tr>
					<td><c:out value="${room.number}" /></td>
					<td><c:out value="${room.seatsAmount}" /></td>
					<td><c:out value="${room.perdayCost}" /></td>
					<td><c:out value="${room.roomStatus.status}" /></td>
					<td><c:out value="${room.roomType.type}" /></td>
					<td><c:out value="${room.description}" /></td>
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