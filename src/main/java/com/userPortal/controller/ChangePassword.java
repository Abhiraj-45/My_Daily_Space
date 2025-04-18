package com.userPortal.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.userPortal.dao.UserDAO;




@WebServlet("/changePassword")
public class ChangePassword extends HttpServlet{
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException ,IOException{
		
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword =  request.getParameter("newPassword");
		
		if(email==null) {
			response.sendRedirect("login.jsp");
			return ;
		}
		
		boolean success = UserDAO.changePassword(email, oldPassword,newPassword);
		
		if(success) {
			request.setAttribute("message", "Password changed successfully!");
		}else {
			request.setAttribute("message", "Incorrect old Password");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("change_password.jsp");
		rd.forward(request, response);
	}
}
