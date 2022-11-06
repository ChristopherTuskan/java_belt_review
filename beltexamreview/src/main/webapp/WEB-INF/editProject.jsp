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
	<div class="col-4 mx-auto bg-dark text-white rounded p-3">
		<h1 class="m-3 text-warning">Edit Project</h1>
		<form:form action="/project/update/${project.getId()}" method="post" modelAttribute="project">
			<div class="form-group m-3">
				<form:label path="title">Project Title:</form:label>
				<form:errors class="text-danger" path="title"/>
				<form:input path="title" value="${project.getTitle()}" class="form-control"/>
			</div>
			<div class="form-group m-3">
				<form:label path="description">Project Description:</form:label>
				<form:errors class="text-danger" path="description"/>
				<form:textarea path="description" value="${project.getDescription()}" class="form-control"/>
			</div>
			<div class="form-group m-3 col-3">
				<form:label path="dueDate">Due Date:</form:label>
				<form:errors class="text-danger" path="dueDate"/>
				<form:input type="date" path="dueDate" value="${project.getDueDate()}" class="form-control"/>
			</div>
			<div class="d-flex justify-content-center">
				<input type="submit" value="Submit" class="m-3 bg-warning"/>
				<a class="m-3" href="/dashboard"><input type=button value="Cancel"></a>
			</div>
		</form:form>
	</div>
</body>
</html>