<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Goals - Daily Space</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        .progress { height: 25px; }
        .progress-bar { transition: width 0.6s ease; }
        .goal-card { transition: transform 0.3s; }
        .goal-card:hover { transform: translateY(-5px); }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>My Goals</h2>
            <div>
	            <a href="dashboard.jsp" class="btn btn-outline-secondary me-2">
	                <i class="bi bi-house-door"></i> Home
	            </a>
	            <a href="goals?action=new" class="btn btn-primary">+ Add New Goal</a>
       		 </div>
        </div>

        <div class="row">
            <c:forEach items="${goals}" var="goal">
                <div class="col-md-6 mb-4">
                    <div class="card goal-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <h5 class="card-title">${goal.title}</h5>
                                <span class="badge bg-${goal.priority == 'High' ? 'danger' : goal.priority == 'Medium' ? 'warning' : 'info'}">
                                    ${goal.priority}
                                </span>
                            </div>
                            <p class="card-text">${goal.description}</p>
                            <div class="mb-3">
                                <span class="badge bg-secondary">${goal.category}</span>
                                <small class="text-muted ms-2">Due: ${goal.targetDate}</small>
                            </div>
                            
                            <div class="progress mb-3">
                                <div class="progress-bar bg-success" role="progressbar" 
                                    style="width: ${goal.progress}%" 
                                    aria-valuenow="${goal.progress}" 
                                    aria-valuemin="0" 
                                    aria-valuemax="100">
                                    ${goal.progress}%
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-end">
                                <a href="goals?action=edit&goalId=${goal.goalId}" class="btn btn-sm btn-outline-primary me-2">Edit</a>
                                <a href="goals?action=delete&goalId=${goal.goalId}" class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Are you sure you want to delete this goal?')">Delete</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>