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
			<h1>Project: <c:out value="${project.getTitle()}"></c:out></h1>
			<a href="/dashboard">Back to Dashboard</a>
		</div>
		<p class="m-3">Project Lead: <c:out value="${project.getLeadUser()}"></c:out></p>
		<form:form action="/project/${project.getId()}/tasks/create" method="post" modelAttribute="task">
			<div class="form-group m-3">
				<form:label path="description">Add a task ticket for this team:</form:label>
				<form:errors class="text-danger" path="description"/>
				<form:textarea path="description" class="form-control"/>
			</div>
			<input type="submit" value="Submit" class="m-3 bg-warning"/>
		</form:form>
		<c:forEach var="task" items="${tasks}">
			<p class="m-3 font-weight-bold">Added by <c:out value="${task.getUser().getUsername()}"></c:out> at <c:out value="${task.getCreatedAt()}"></c:out></p>
			<p class="m-3"><c:out value="${task.getDescription()}"></c:out></p>
		</c:forEach>
	</div>
</body>
</html>