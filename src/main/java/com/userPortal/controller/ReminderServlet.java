package com.userPortal.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.userPortal.dao.ReminderDAO;
import com.userPortal.model.Reminder;

@WebServlet("/reminder")
public class ReminderServlet extends HttpServlet {

    private ReminderDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new ReminderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");

        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            if ("add".equals(action)) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String timeString = request.getParameter("reminder_time");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime reminderTime = LocalDateTime.parse(timeString, formatter);

                Reminder reminder = new Reminder(userEmail, title, description, reminderTime);
                reminder.setCreatedAt(LocalDateTime.now());

                boolean success = dao.addReminder(reminder);
                request.setAttribute(success ? "message" : "error",
                        success ? "Reminder added successfully." : "Failed to add reminder.");

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean deleted = dao.deleteReminder(id);
                request.setAttribute(deleted ? "message" : "error",
                        deleted ? "Reminder deleted successfully." : "Failed to delete reminder.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        List<Reminder> reminders = dao.getReminderByUser(userEmail);
        request.setAttribute("reminders", reminders);

        request.getRequestDispatcher("reminders.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");

        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ReminderDAO dao = new ReminderDAO();
        List<Reminder> reminders = dao.getReminderByUser(userEmail);
        request.setAttribute("reminders", reminders);

        request.getRequestDispatcher("reminders.jsp").forward(request, response);
    }

}
