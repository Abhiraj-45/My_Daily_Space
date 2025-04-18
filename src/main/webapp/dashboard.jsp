<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String email = (String) session.getAttribute("email");
    if (email == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Daily Space - Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/dashboard.css">
    <style>
        /* Add these styles to your dashboard.css */
        .feature-box {
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .feature-box:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        #dashboard-features {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- Header with Welcome Message -->
        <header class="d-flex justify-content-between align-items-center py-3 mb-4 border-bottom">
            <div class="welcome-box">
                <h2>Welcome, <%= email %>!</h2>
                <p id="last-login" class="text-muted">Last login: <!-- Add time dynamically --></p>
            </div>
            <nav class="d-flex gap-3">
                <a href="change_password.jsp" class="btn btn-outline-primary">Change Password</a>
                <a href="update_profile.jsp" class="btn btn-outline-primary">Update Profile</a>
                <a href="logout" class="btn btn-danger">Logout</a>
            </nav>
        </header>
        
        <!-- Feature Boxes -->
        <section id="dashboard-features" class="dashboard-features mb-5">
  
		  <div class="feature-box card h-100">
		    <a href="notes.jsp" class="card-body d-flex flex-column text-decoration-none text-dark">
		      <h3 class="card-title">📝 My Notes</h3>
		      <p class="card-text">Create and manage your personal notes</p>
		    </a>
		  </div>
		
		  <div class="feature-box card h-100">
		    <a href="reminder" class="card-body d-flex flex-column text-decoration-none text-dark">
		      <h3 class="card-title">⏰ Reminders</h3>
		      <p class="card-text">Set important reminders</p>
		    </a>
		  </div>
		
		  <div class="feature-box card h-100">
		    <a href="transactions" class="card-body d-flex flex-column text-decoration-none text-dark">
		      <h3 class="card-title">💰 Expense Manager</h3>
		      <p class="card-text">Track your spending</p>
		    </a>
		  </div>
		
		  <div class="feature-box card h-100">
		    <a href="goals?action=list" class="card-body d-flex flex-column text-decoration-none text-dark">
		      <h3 class="card-title">🎯 Goal Tracker</h3>
		      <p class="card-text">Manage your personal goals</p>
		    </a>
		  </div>
		
		  <div class="feature-box card h-100">
		    <a href="file-upload.jsp" class="card-body d-flex flex-column text-decoration-none text-dark">
		      <h3 class="card-title">📁 File Upload</h3>
		      <p class="card-text">Store your important files</p>
		    </a>
		  </div>
		
		  <div class="feature-box card h-100">
		    <a href="todo" class="card-body d-flex flex-column text-decoration-none text-dark">
		      <h3 class="card-title">✅ To-Do List</h3>
		      <p class="card-text">Manage daily tasks</p>
		    </a>
		  </div>
		
		</section>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Add last login time
        document.getElementById('last-login').textContent = 'Last login: ' + 
            new Date().toLocaleString();
    </script>
</body>
</html>