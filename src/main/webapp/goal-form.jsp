<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${goal == null ? 'Add New' : 'Edit'} Goal - Daily Space</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
</head>
<body>
    <div class="container mt-4">
        <h2>${goal == null ? 'Add New' : 'Edit'} Goal</h2>
        
        <form action="goals" method="post" class="mt-4">
            <input type="hidden" name="action" value="${goal == null ? 'insert' : 'update'}">
            <c:if test="${goal != null}">
                <input type="hidden" name="goalId" value="${goal.goalId}">
            </c:if>
            
            <div class="mb-3">
                <label for="title" class="form-label">Goal Title</label>
                <input type="text" class="form-control" id="title" name="title" 
                       value="${goal.title}" required>
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" 
                          rows="3">${goal.description}</textarea>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="category" class="form-label">Category</label>
                    <select class="form-select" id="category" name="category">
                        <option value="Fitness" ${goal.category == 'Fitness' ? 'selected' : ''}>Fitness</option>
                        <option value="Career" ${goal.category == 'Career' ? 'selected' : ''}>Career</option>
                        <option value="Learning" ${goal.category == 'Learning' ? 'selected' : ''}>Learning</option>
                        <option value="Personal" ${goal.category == 'Personal' ? 'selected' : ''}>Personal</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <label for="priority" class="form-label">Priority</label>
                    <select class="form-select" id="priority" name="priority">
                        <option value="Low" ${goal.priority == 'Low' ? 'selected' : ''}>Low</option>
                        <option value="Medium" ${goal.priority == 'Medium' ? 'selected' : ''}>Medium</option>
                        <option value="High" ${goal.priority == 'High' ? 'selected' : ''}>High</option>
                    </select>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="targetDate" class="form-label">Target Date</label>
                    <input type="date" class="form-control" id="targetDate" name="targetDate" 
                           value="${goal.targetDate}" required>
                </div>
                <div class="col-md-6">
                    <label for="progress" class="form-label">Progress (%)</label>
                    <input type="range" class="form-range" id="progress" name="progress" 
                           min="0" max="100" value="${goal != null ? goal.progress : 0}"
                           oninput="progressValue.value = this.value">
                    <output id="progressValue">${goal != null ? goal.progress : 0}</output>%
                </div>
            </div>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <a href="goals" class="btn btn-secondary me-md-2">Cancel</a>
                <button type="submit" class="btn btn-primary">Save Goal</button>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Initialize date picker with min date today
        document.getElementById('targetDate').min = new Date().toISOString().split('T')[0];
    </script>
</body>
</html>