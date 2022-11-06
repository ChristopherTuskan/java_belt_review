<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ page isErrorPage="true" %>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/>
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
</head>
<body>
	<div class="col-4 mx-auto">
		<div class="d-flex justify-content-between m-3">
			<h1>Project Details</h1>
			<a href="/dashboard">Back to the Dashboard</a>
		</div>
		<div class="m-3">
			<table class="table table-borderless">
				<tr>
					<td>Project Title:</td>
					<td><c:out value="${project.getTitle()}"></c:out></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><c:out value="${project.getDescription()}"></c:out></td>
				</tr>
				<tr>
					<td>Due Date:</td>
					<td><c:out value="${project.getDueDate()}"></c:out></td>
				</tr>
			</table>
		</div>
		<c:if test="${project.getUsers().contains(user)}">
			<div class="d-flex justify-content-between m-3">
				<a href="/project/${project.getId()}/tasks" class="m-3">See tasks</a>
				<c:if test="${project.getLeadUser().equals(user.getUsername())}">
					<a href="/project/destroy/${project.getId()}"><input class="m-3 btn-danger" type=button value="Delete Project"></a>
				</c:if>
			</div>
		</c:if>
	</div>
</body>
</html>