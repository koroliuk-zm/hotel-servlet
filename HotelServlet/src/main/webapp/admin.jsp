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
<title>${currentUser.name}${currentUser.surname}</title>
</head>
<body>
<jsp:include page="/admin_navbar.jsp"></jsp:include>
	<br>
	<br>
	<div class="container">
    <div class="row">
        <h1 class="text-center" <fmt:message key="header" />">Administrator page</h1>
    </div>
		<br>
		<br>
		<table>
		<tbody>
			<tr>
				<th><fmt:message key="admin.login" /></th>
				<td>${currentUser.login}</td>
			</tr>
			<tr>
				<th><fmt:message key="register.form.name" /></th>
				<td>${currentUser.name}</td>
			</tr>
			<tr>
				<th><fmt:message key="register.form.surname" /></th>
				<td>${currentUser.surname}</td>
			</tr>
			</tbody>
		</table>
		<br> <br>
		<table class="table table-success table-striped">
        <thead class="table-dark">
        <tr>
            <th> Users menu</th>
            <th> Rooms menu</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <ul>
                    <li><a class="nav-link" href="main?action=getAllUsers"><fmt:message key="admin.users" /></a></li>
                    <li><a class="nav-link" href="main?action=addUserLink"><fmt:message key="admin.addUser" /></a></li>
                </ul>
            </td>
            <td>
                <ul>
                    <li><a class="nav-link" href="main?action=getAllRooms"><fmt:message key="admin.rooms" /></a></li>
                    <li><a class="nav-link" href="main?action=addRoomLink"><fmt:message key="admin.addRoom" /></a></li>
                </ul>
            </td>
        </tr>
        </tbody>
    </table>
	</div>
</body>
</html>