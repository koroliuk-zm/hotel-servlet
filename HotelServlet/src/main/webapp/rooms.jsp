<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
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
<style type="text/css">
	.roomEdit {
		display: ${roomEditVis}
	}
	.roomOrder {
		display: ${roomOrderVis}
	}
	.roomDelete {
		display: ${roomDeleteVis}
	}
	.makeRequest {
		display: ${makeRequestVis}
	}
</style>
<meta charset="UTF-8">
<title><fmt:message key="rooms.title"/></title>
</head>
<body>
	<c:choose>
		<c:when test="${currentUserRole eq 'guest'}">
			<jsp:include page="/main_navbar.jsp"></jsp:include>
		</c:when>
		<c:when test="${currentUserRole eq 'client'}">
			<jsp:include page="/user_navbar.jsp"></jsp:include>
		</c:when>
		<c:when test="${currentUserRole eq 'admin'}">
			<jsp:include page="/admin_navbar.jsp"></jsp:include>
		</c:when>
	</c:choose>
	<br>	
	<br>
	<div class="container">
    <div class="row">
        <h1 class="text-center"> <fmt:message key="rooms.header" /></h1>
    </div>
		<br><br>
			<form action="main" method="get">
				<input type="hidden" name="action" value="getAllRooms">
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
				<th style="width:10%; text-align: right;" class="roomEdit"><fmt:message key="c.action.edit" /></th>
				<th style="width:10%; text-align: right;" class="roomDelete"><fmt:message key="c.action.delete" /></th>
				<th style="width:10%; text-align: right;" class="roomOrder"><fmt:message key="rooms.form.main.order" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="room" items="${rooms}">
				<tr>
					<td><c:out value="${room.number}" /></td>
					<td><c:out value="${room.seatsAmount}" /></td>
					<td><c:out value="${room.perdayCost}" /></td>
					<td><c:out value="${room.roomStatus.status}" /></td>
					<td><c:out value="${room.roomType.type}" /></td>
					<td><c:out value="${room.description}" /></td>
					<td class="roomEdit" style="width:10%; text-align: right;"><a class="btn btn-primary btn-sm"
						href="main?action=updateRoomPage&rId=${room.id}"><fmt:message
								key="c.button.edit" /></a></td>
					<td class="roomDelete" style="width:10%; text-align: right;"><a class="btn btn-danger btn-sm"
						href="main?action=deleteRoom&rId=${room.id}"><fmt:message
								key="c.button.delete" /></a></td>
					<td class="roomOrder" style="width:10%; text-align: right;"><a class="btn btn-primary btn-sm"
						href="main?action=orderPage&rId=${room.id}"><fmt:message
								key="c.button.order" /></a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<p>
		${paginationNav}
		</p>
		<div class="makeRequest">
		<a class="btn btn-success mb-2"
						href="main?action=requestPage"><fmt:message
								key="rooms.form.main.request" /></a>
		</div>
	</div>
</body>
</html>