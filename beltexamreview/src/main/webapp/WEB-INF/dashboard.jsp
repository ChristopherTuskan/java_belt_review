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
	<div class="col-6 mx-auto">
		<div class="d-flex justify-content-between">
			<h1 class="m-3 text-primary">Welcome, ${user.getUsername()}</h1>
			<a class="m-2" href="/logout">logout</a>
		</div>
		<div class="d-flex justify-content-between">
			<p class="m-3">All Other Projects</p>
			<a class="m-3" href="/project/new"><input type=button value="+ New Project"></a>
		</div>
		<table class="table table-bordered">
			<tr>
				<th>Project</th>
				<th>Team Lead</th>
				<th>Due Date</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="project" items="${projects}">
				<c:if test="${!project.getUsers().contains(user)}">
					<tr>
						<td><a href="/project/show/${project.getId()}"><c:out value="${project.getTitle()}"></c:out></a></td>
						<td><c:out value="${project.getLeadUser()}"></c:out></td>
						<td><c:out value="${project.getDueDate()}"></c:out></td>
						<td><a href="/project/join/${project.getId()}">Join Team</a></td>
					</tr>
				</c:if>
    		</c:forEach>
		</table>
		<p class="m-3">Your Projects</p>
		<table class="table table-bordered">
			<tr>
				<th>Project</th>
				<th>Team Lead</th>
				<th>Due Date</th>
				<th>Actions</th>
			</tr>
  			<c:forEach var="project" items="${projects}">
  				<c:if test="${project.getUsers().contains(user)}">
					<tr>
						<td><a href="/project/show/${project.getId()}"><c:out value="${project.getTitle()}"></c:out></a></td>
						<td><c:out value="${project.getLeadUser()}"></c:out></td>
						<td><c:out value="${project.getDueDate()}"></c:out></td>
						<c:choose>
							<c:when test="${project.getLeadUser().equals(user.getUsername())}"><td><a href="/project/edit/${project.getId()}">edit</a></td></c:when>
							<c:otherwise><td><a href="/project/leave/${project.getId()}">Leave Team</a></td></c:otherwise>
						</c:choose>
					</tr>
				</c:if>
    		</c:forEach>
		</table>
	</div>
</body>
</html>