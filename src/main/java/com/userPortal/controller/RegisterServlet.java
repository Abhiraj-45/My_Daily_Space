package com.userPortal.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.userPortal.dao.UserDAO;
import com.userPortal.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request , HttpServletResponse response)throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password  = request.getParameter("password");
		
		
		User user = new User(name, email, password);
		
		UserDAO dao = new UserDAO();
		boolean success = dao.registerUser(user);
		
		if(success) {
			response.sendRedirect("login.jsp");
		}else {
			response.sendRedirect("register.jsp?error=true");
		}
	}
}
