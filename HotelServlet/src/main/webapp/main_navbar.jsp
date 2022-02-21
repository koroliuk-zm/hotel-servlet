<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="messages" />
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container-fluid">
		<a class="navbar-brand" href="main?action=default"><fmt:message
				key="nav.hotel" /></a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNavAltMarkup"
			aria-controls="navbarNavAltMarkup" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-link active" href="login.jsp"><fmt:message
						key="nav.login" /></a> <a class="nav-link" href="main?action=default"><fmt:message
						key="nav.home" /></a>
			</div>
		</div>
	</div>
	<div class="d-flex">
		<div class="navbar-nav">
			<a class="nav-link" href="register.jsp"><fmt:message
					key="nav.register" /></a> <a
				class="nav-link"
				href="main?action=changeLocale&language=ua&country=UK">UK</a><a
				class="nav-link"
				href="main?action=changeLocale&language=en&country=US">Eng</a>
		</div>
	</div>
</nav>
