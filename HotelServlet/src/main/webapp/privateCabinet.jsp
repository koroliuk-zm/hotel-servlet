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
</style>
<meta charset="UTF-8">
<title>${currentUser.name}${currentUser.surname}</title>
</head>
<body>
	<jsp:include page="/user_navbar.jsp"></jsp:include>
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
				<fmt:message key="privateCabinet.orders" />
			</h3>
			<br>
			<table class="table table-success table-striped">
				<thead class="table-dark">
					<tr>
						<th><fmt:message key="privateCabinet.table.login" /></th>
						<th><fmt:message key="privateCabinet.table.roomNumber" /></th>
						<th><fmt:message key="privateCabinet.table.orderDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckInDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckOutDate" /></th>
						<th><fmt:message key="privateCabinet.table.totalCost" /></th>
						<th><fmt:message key="privateCabinet.table.orderStatus" /></th>
						<th><fmt:message key="privateCabinet.table.payOrder" /></th>
						<th><fmt:message key="privateCabinet.table.cancelOrder" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${unpaidOrders}">
						<tr>
							<td><c:out value="${order.user.login}" /></td>
							<td><c:out value="${order.room.number}" /></td>
							<td><tag:dateTime value="${order.orderDate}" /></td>
							<td><tag:dateTime value="${order.checkInDate}" /></td>
							<td><tag:dateTime value="${order.checkOutDate}" /></td>
							<td><c:out value="${order.totalCost}" /></td>
							<td><c:out value="${order.orderStatus.status}" /></td>
							<td style="width:10%; text-align: right;"><a class="btn btn-primary"
						href="main?action=payRoomOrder&oId=${order.id}"><fmt:message
								key="order.button.pay" /></a></td>
					<td style="width:10%; text-align: right;"><a class="btn btn-danger"
						href="main?action=cancelOrder&oId=${order.id}"><fmt:message
								key="order.button.cancel" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br><br>
			<h3 class="text-center">
				<fmt:message key="privateCabinet.ordersHistory" />
			</h3>
			<br>
			<table class="table table-success table-striped">
				<thead class="table-dark">
					<tr>
						<th><fmt:message key="privateCabinet.table.login" /></th>
						<th><fmt:message key="privateCabinet.table.roomNumber" /></th>
						<th><fmt:message key="privateCabinet.table.orderDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckInDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckOutDate" /></th>
						<th><fmt:message key="privateCabinet.table.totalCost" /></th>
						<th><fmt:message key="privateCabinet.table.orderStatus" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${ordersHistory}">
						<tr>
							<td><c:out value="${order.user.login}" /></td>
							<td><c:out value="${order.room.number}" /></td>
							<td><tag:dateTime value="${order.orderDate}" /></td>
							<td><tag:dateTime value="${order.checkInDate}" /></td>
							<td><tag:dateTime value="${order.checkOutDate}" /></td>
							<td><c:out value="${order.totalCost}" /></td>
							<td><c:out value="${order.orderStatus.status}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br><br>
			<h3 class="text-center">
				<fmt:message key="privateCabinet.requests" />
			</h3>
			<br>
			<table class="table table-success table-striped">
				<thead class="table-dark">
					<tr>
						<th><fmt:message key="privateCabinet.table.login" /></th>
						<th><fmt:message key="privateCabinet.table.orderDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckInDate" /></th>
						<th><fmt:message key="privateCabinet.table.orderCheckOutDate" /></th>
						<th><fmt:message key="privateCabinet.table.roomType" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="request" items="${userRequests}">
						<tr>
							<td><c:out value="${request.user.login}" /></td>
							<td><tag:dateTime value="${request.requestDate}" /></td>
							<td><tag:dateTime value="${request.checkInDate}" /></td>
							<td><tag:dateTime value="${request.checkOutDate}" /></td>
							<td><c:out value="${request.roomType.type}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br><br><br>
		</div>
</body>
</html>