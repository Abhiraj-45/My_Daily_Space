package com.userPortal.controller;

import com.userPortal.dao.TodoDAO;
import com.userPortal.model.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
    private TodoDAO todoDAO;

    @Override
    public void init() throws ServletException {
        todoDAO = new TodoDAO();
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

        switch (action) {
            case "add":
                addTodo(request, response, userEmail);
                break;
            case "update":
                updateStatus(request, response);
                break;
            case "delete":
                deleteTodo(request, response);
                break;
        }
    }

    private void addTodo(HttpServletRequest request, HttpServletResponse response, String userEmail) throws IOException {
        String task = request.getParameter("task");
        String status = request.getParameter("status");

        Todo todo = new Todo(userEmail, task, status, LocalDateTime.now());
        boolean success = todoDAO.addTodo(todo);

        if (success) {
            request.getSession().setAttribute("message", "Task added successfully.");
        } else {
            request.getSession().setAttribute("error", "Failed to add task.");
        }
        response.sendRedirect("todos.jsp");
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        boolean success = todoDAO.updateTodoStatus(id, status);

        if (success) {
            request.getSession().setAttribute("message", "Task status updated.");
        } else {
            request.getSession().setAttribute("error", "Failed to update status.");
        }
        response.sendRedirect("todos.jsp");
    }

    private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        boolean success = todoDAO.deleteTodo(id);
        if (success) {
            request.getSession().setAttribute("message", "Task deleted.");
        } else {
            request.getSession().setAttribute("error", "Failed to delete task.");
        }
        response.sendRedirect("todos.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");

        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Todo> todoList = todoDAO.getTodosByUser(userEmail);
        request.setAttribute("todos", todoList);
        request.getRequestDispatcher("todos.jsp").forward(request, response);
    }
}
