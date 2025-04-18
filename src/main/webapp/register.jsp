<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Registration</title>
<link rel="stylesheet" href="css/register.css">
</head>
<body>

<div class="container">
    <h2 style="text-align:center;">Register Here</h2>

    <form action="register" method="post">
        <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text" id="name" name="name" placeholder="Enter your full name" required>
        </div>

        <div class="form-group">
            <label for="email">Email Address:</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Create a password" required>
        </div>

        <input type="submit" value="Register">
    </form>

    <!-- Success message -->
    <%
        String success = request.getParameter("success");
        if ("true".equals(success)) {
    %>
        <div class="message success">
            <p>Your registration was successful!</p>
        </div>
    <%
        }
    %>

    <p style="text-align:center;">
        <a href="index.jsp">Back</a>
    </p>
</div>

</body>
</html>