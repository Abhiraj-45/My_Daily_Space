<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="css/register.css">
</head>
<body>

	<div class="container">
		<h2>Login</h2>
		
		<form action="login" method="post">
			<div class="form-group">
				<label for="email">Email</label>
				<input type="email" name="email" id="email" placeholder="Enter your email" required>
			</div>
			
			<div class="form-group">
				<label for="password">Password</label>
				<input type="password" name="password" id="password" placeholder="Enter your password" required>
			</div>
			
			<input type="submit" value="Login">
		</form>

		<!-- Error message alignment to the right -->
		<%
			String error = request.getParameter("error");
			if ("true".equals(error)) {
		%>
			<div class="message">
				<p>Invalid email or password</p>
			</div>
		<%
			}
		%>

		<p style="text-align: center;">
			Don't have an account? <a href="register.jsp">Register</a>
		</p>
	</div>

</body>
</html>