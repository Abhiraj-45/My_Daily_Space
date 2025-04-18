<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.userPortal.model.Reminder" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    String userEmail = (String) session.getAttribute("email");
    if (userEmail == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Reminder> reminders = (List<Reminder>) request.getAttribute("reminders");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reminders - My Daily Space</title>
    <link rel="stylesheet" href="css/reminders.css">
</head>
<body>
<div class="container">
    <h2>My Reminders</h2>

    <!-- Message display -->
    <% if (request.getAttribute("message") != null) { %>
        <p class="success"><%= request.getAttribute("message") %></p>
    <% } else if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <!-- Reminder Form -->
    <section class="form-section">
        <form action="reminder" method="post">
            <input type="hidden" name="action" value="add">

            <label for="title">Title:</label><br>
            <input type="text" name="title" id="title" required><br><br>

            <label for="description">Description:</label><br>
            <textarea name="description" id="description" rows="4" required></textarea><br><br>

            <label for="reminder_time">Reminder Time:</label><br>
            <input type="datetime-local" name="reminder_time" id="reminder_time" required><br><br>

            <input type="submit" value="Add Reminder">
        </form>
    </section>

    <hr>

    <!-- Display Reminders -->
    <section class="reminders-section">
        <h3>Your Upcoming Reminders</h3>

        <%
            if (reminders != null && !reminders.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                for (Reminder rem : reminders) {
                    String formattedReminderTime = rem.getReminderTime().format(formatter);
                    String formattedCreatedAt = rem.getCreatedAt().format(formatter);
        %>

        <div class="reminder-card">
            <h4><%= rem.getTitle() %></h4>
            <p><%= rem.getDescription() %></p>
            <small>Reminder Time: <%= formattedReminderTime %></small><br>
            <small>Created At: <%= formattedCreatedAt %></small>

            <form action="reminder" method="post" style="margin-top: 10px;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= rem.getId() %>">
                <button type="submit" class="delete-btn">Delete</button>
            </form>
        </div>

        <%
                }
            } else {
        %>
            <p>No reminders yet. Add one above!</p>
        <%
            }
        %>
    </section>

    <!-- Navigation -->
    <p style="margin-top: 20px;">
        <a href="dashboard.jsp">Back to Dashboard</a>
    </p>
</div>
</body>
</html>
