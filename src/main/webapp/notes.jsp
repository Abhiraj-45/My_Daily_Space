<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.userPortal.dao.NotesDAO, com.userPortal.model.Notes" %>

<%
    String userEmail = (String) session.getAttribute("email");
    if(userEmail == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Notes - My Daily Space</title>
    <link rel="stylesheet" href="css/notes.css">
</head>
<body>

<div class="container">
    <h2>My Notes</h2>

    <%
        String msg = (String) request.getAttribute("message");
        String error = (String) request.getAttribute("error");
        if(msg != null){
    %>
        <p class="success"><%= msg %></p>
    <%
        } else if(error != null){
    %>
        <p class="error"><%= error %></p>
    <%
        }
    %>

    <!-- Add Note Form -->
    <form action="note" method="post">
        <input type="hidden" name="action" value="add">

        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required>

        <label for="content">Content:</label>
        <textarea name="content" id="content" rows="5" required></textarea>

        <input type="submit" value="Add Note">
    </form>

    <hr>

    <!-- Notes List -->
    <div class="notes-list">
        <h3>Your Saved Notes</h3>
        <%
            NotesDAO dao = new NotesDAO();
            List<Notes> notes = dao.getNotesByUser(userEmail);
            if (notes != null && !notes.isEmpty()) {
                for (Notes note : notes) {
        %>
            <div class="note-card">
			    <h4><%= note.getTitle() %></h4>
			    <p><%= note.getContent() %></p>
			    <small>Created on: <%= note.getCreatedAt() %></small>
			    
  	  <div class="note-card-footer">
        <form action="note" method="post">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="<%= note.getId() %>">
            <input type="submit" value="Delete" class="delete-btn">
        </form>
    </div>
</div>

        <%
                }
            } else {
        %>
            <p>No notes yet. Start by adding one above!</p>
        <%
            }
        %>
    </div>

    <p style="margin-top: 20px;"><a href="dashboard.jsp">Back to Dashboard</a></p>

</div>
</body>
</html>
