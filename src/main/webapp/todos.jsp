<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.userPortal.model.Todo" %>
<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
    String message = (String) session.getAttribute("message");
    String error = (String) session.getAttribute("error");
    session.removeAttribute("message");
    session.removeAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My To-Do List</title>
    <link rel="stylesheet" href="css/todos.css">
</head>
<body>
<div class="container">
    <h2>My To-Do List</h2>

    <% if (message != null) { %>
        <div class="alert success"><%= message %></div>
    <% } else if (error != null) { %>
        <div class="alert error"><%= error %></div>
    <% } %>

    <form action="todo" method="post" class="todo-form">
        <input type="hidden" name="action" value="add">
        <input type="text" name="task" placeholder="Enter task..." required>
        <select name="status">
            <option value="Pending">Pending</option>
            <option value="In Progress">In Progress</option>
            <option value="Completed">Completed</option>
        </select>
        <button type="submit">Add Task</button>
       <button onclick="window.location.href='dashboard.jsp'" class="btn btn-outline-secondary me-2">
    Home
</button>

    </form>

    <table class="todo-table">
        <thead>
        <tr>
            <th>#</th>
            <th>Task</th>
            <th>Status</th>
            <th>Created At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (todos != null && !todos.isEmpty()) {
            int count = 1;
            for (Todo t : todos) {
        %>
            <tr>
                <td><%= count++ %></td>
                <td><%= t.getTask() %></td>
                <td><%= t.getStatus() %></td>
                <td><%= t.getCreatedAt().toString().replace("T", " ") %></td>
                <td>
                    <form action="todo" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <select name="status">
                            <option <%= "Pending".equals(t.getStatus()) ? "selected" : "" %>>Pending</option>
                            <option <%= "In Progress".equals(t.getStatus()) ? "selected" : "" %>>In Progress</option>
                            <option <%= "Completed".equals(t.getStatus()) ? "selected" : "" %>>Completed</option>
                        </select>
                        <button type="submit">Update</button>
                    </form>
                    <form action="todo" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>
                </td>
            </tr>
        <% } } else { %>
            <tr><td colspan="5">No tasks found.</td></tr>
        <% } %>
        </tbody>
        
    </table>
    
</div>
	
</body>
</html>
