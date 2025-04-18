<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	
 	String email = (String) session.getAttribute("email");
 	if(email==null){
 		response.sendRedirect("login.jsp");
 	}
 
 %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Password</title>
<link rel="stylesheet" href="css/register.css">
</head>
<body>
 	<div class="container">
		<h2 style="text-align:center;">Change Password</h2>
		
		<%
			String msg = (String)request.getAttribute("message");
			if(msg!=null){
		%>
			<p style="color:green;"><%=msg %></p>
		<%
			}
		%>
	
		<form action="changePassword" method="post">
			<input type="password" name="oldPassword" placeholder="Old Password" required><br>
			<input type="password" name= "newPassword" placeholder="New Password" required><br>
			<input type="submit" value="Change Password">
			
		</form>
		
		<p style="text-align:center;">
			 <a href="dashboard.jsp">Back</a>
		</p>
	</div>
</body>
</html>