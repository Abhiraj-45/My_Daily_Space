package com.userPortal.controller;

import com.userPortal.dao.TransactionDAO;
import com.userPortal.dao.TransactionDAO.CategorySummary;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "TransactionAnalyticsServlet", urlPatterns = {"/analytics"})
public class TransactionAnalyticsServlet extends HttpServlet {
    private TransactionDAO transactionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userEmail = (String) request.getSession().getAttribute("email");
        String monthYear = request.getParameter("month") != null ? 
            request.getParameter("month") : 
            java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));

        try {
            // Get totals
            BigDecimal income = transactionDAO.getTotalIncome(userEmail, monthYear);
            BigDecimal expense = transactionDAO.getTotalExpense(userEmail, monthYear);
            BigDecimal balance = income.subtract(expense);

            // Get category breakdowns
            List<CategorySummary> incomeCategories = 
                transactionDAO.getCategorySummary(userEmail, "INCOME", monthYear);
            List<CategorySummary> expenseCategories = 
                transactionDAO.getCategorySummary(userEmail, "EXPENSE", monthYear);

            // Prepare JSON response
            String jsonResponse = String.format(
                "{\"income\": %.2f, \"expense\": %.2f, \"balance\": %.2f, " +
                "\"incomeCategories\": %s, \"expenseCategories\": %s}",
                income, expense, balance,
                convertToJson(incomeCategories),
                convertToJson(expenseCategories)
            );

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error\"}");
        }
    }

    private String convertToJson(List<CategorySummary> categories) {
        StringBuilder json = new StringBuilder("[");
        for (CategorySummary cs : categories) {
            json.append(String.format(
                "{\"category\": \"%s\", \"total\": %.2f},",
                cs.getCategory(), cs.getTotal()
            ));
        }
        if (!categories.isEmpty()) {
            json.deleteCharAt(json.length() - 1); // Remove trailing comma
        }
        json.append("]");
        return json.toString();
    }
}