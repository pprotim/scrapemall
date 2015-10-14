<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/AdminLTE.css" rel="stylesheet" type="text/css" />
<link href="css/custom.css" rel="stylesheet" type="text/css" />

</head>

<body>
		
	<div class="login-banner" id="login-banner">
		<div class="form-box bg-gray" id="login-box">
			<div class="header">
				<h3>Adv Control Center</h3>
			</div>
			<form method="POST"  action="<c:url value='/login'/>"  name="login_form" id="login_form"
				class="bg-gray">
				<div class="body bg-gray">
					<div class="form-group">
						<input type="text" name="username" id="username"
							class="form-control" placeholder="User ID" value="test"/>
					</div>
					<div class="form-group">
						<input type="password" name="password" id="password"
							class="form-control" placeholder="Password" value="test"/>
					</div>
					
				</div>
				<div class="footer bg-gray">
					<button type="submit" class="btn bg-red btn-block">Login</button>
				</div>
			</form>
			
			
			<c:if test="${not empty errorMsg}">
				<div class="alert alert-danger" style="margin-right: 20px;">
					<p>${errorMsg}</p>
				</div>
				<p>&nbsp;</p>
			</c:if>
		</div>
	</div>
</body>

</html>