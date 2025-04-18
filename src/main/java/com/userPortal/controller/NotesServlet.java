package com.userPortal.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.userPortal.dao.NotesDAO;
import com.userPortal.model.Notes;

@WebServlet("/note")
public class NotesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");

        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        NotesDAO dao = new NotesDAO();

        if ("add".equalsIgnoreCase(action)) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");

            Notes note = new Notes();
            note.setUserEmail(userEmail);
            note.setTitle(title);
            note.setContent(content);
            note.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            boolean success = dao.addNote(note);

            if (success) {
                request.setAttribute("message", "Note added successfully!");
            } else {
                request.setAttribute("error", "Something went wrong while adding the note.");
            }

        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean deleted = dao.deleteNote(id);

                if (deleted) {
                    request.setAttribute("message", "Note deleted successfully.");
                } else {
                    request.setAttribute("error", "Failed to delete the note.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid note ID.");
            }
        }

        request.getRequestDispatcher("notes.jsp").forward(request, response);
    }
}
