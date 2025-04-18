package com.userPortal.controller;

import com.userPortal.dao.GoalDAO;
import com.userPortal.model.Goal;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "GoalServlet", urlPatterns = {"/goals"})
public class GoalServlet extends HttpServlet {
    private GoalDAO goalDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        goalDAO = new GoalDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("email");

        try {
            if (action == null) {
                action = "list"; // Default action
            }

            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteGoal(request, response);
                    break;
                case "list":
                default:
                    listGoals(request, response, userEmail);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("email");

        try {
            switch (action) {
                case "insert":
                    insertGoal(request, response, userEmail);
                    break;
                case "update":
                    updateGoal(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listGoals(HttpServletRequest request, HttpServletResponse response, String userEmail)
            throws ServletException, IOException {
        List<Goal> goals = goalDAO.getGoalsByUser(userEmail);
        request.setAttribute("goals", goals);
        RequestDispatcher dispatcher = request.getRequestDispatcher("goal-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("goal-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int goalId = Integer.parseInt(request.getParameter("goalId"));
        Goal goal = goalDAO.getGoalById(goalId);
        request.setAttribute("goal", goal);
        RequestDispatcher dispatcher = request.getRequestDispatcher("goal-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertGoal(HttpServletRequest request, HttpServletResponse response, String userEmail)
            throws IOException {
        Goal goal = new Goal();
        goal.setUserEmail(userEmail);
        goal.setTitle(request.getParameter("title"));
        goal.setDescription(request.getParameter("description"));
        goal.setCategory(request.getParameter("category"));
        goal.setTargetDate(Date.valueOf(request.getParameter("targetDate")));
        goal.setPriority(request.getParameter("priority"));
        goal.setProgress(Integer.parseInt(request.getParameter("progress")));
        
        goalDAO.addGoal(goal);
        response.sendRedirect("goals");
    }

    private void updateGoal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int goalId = Integer.parseInt(request.getParameter("goalId"));
        Goal goal = new Goal();
        goal.setGoalId(goalId);
        goal.setTitle(request.getParameter("title"));
        goal.setDescription(request.getParameter("description"));
        goal.setCategory(request.getParameter("category"));
        goal.setTargetDate(Date.valueOf(request.getParameter("targetDate")));
        goal.setPriority(request.getParameter("priority"));
        goal.setProgress(Integer.parseInt(request.getParameter("progress")));
        
        goalDAO.updateGoal(goal);
        response.sendRedirect("goals");
    }

    private void deleteGoal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int goalId = Integer.parseInt(request.getParameter("goalId"));
        goalDAO.deleteGoal(goalId);
        response.sendRedirect("goals");
    }
}