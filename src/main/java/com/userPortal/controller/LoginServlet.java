package com.userPortal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.userPortal.dao.UserDAO;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException ,IOException{
		
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();

		
		UserDAO dao = new UserDAO();
		
		boolean isValidUser = dao.validateUser(email, password);
		
		if(isValidUser) {
			HttpSession session = request.getSession();
			session.setAttribute("email",email);
			response.sendRedirect("dashboard.jsp");
		}else {
			response.sendRedirect("login.jsp?error=true");
		}
	}
}