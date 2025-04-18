package com.userPortal.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate the session to logout the user
        HttpSession session = request.getSession(false); // false means don't create new if it doesn't exist
        if (session != null) {
            session.invalidate();
        }

        // Redirect to login page
        response.sendRedirect("login.jsp");
    }
}