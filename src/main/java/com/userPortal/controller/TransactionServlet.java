package com.userPortal.controller;

import com.userPortal.dao.TransactionDAO;
import com.userPortal.model.Transaction;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "TransactionServlet", urlPatterns = {"/transactions"})
public class TransactionServlet extends HttpServlet {
    private TransactionDAO transactionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        transactionDAO = new TransactionDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("email");

        try {
            if (action == null) {
                listTransactions(request, response, userEmail);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteTransaction(request, response);
                        break;
                    default:
                        listTransactions(request, response, userEmail);
                        break;
                }
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("email");

        try {
            switch (action) {
                case "insert":
                    insertTransaction(request, response, userEmail);
                    break;
                case "update":
                    updateTransaction(request, response);
                    break;
                default:
                    listTransactions(request, response, userEmail);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTransactions(HttpServletRequest request, HttpServletResponse response, String userEmail)
            throws SQLException, ServletException, IOException {
        // Get filter parameters
        String typeFilter = request.getParameter("type");
        String categoryFilter = request.getParameter("category");
        String dateFrom = request.getParameter("from");
        String dateTo = request.getParameter("to");
        String keyword = request.getParameter("search");

        List<Transaction> transactions = transactionDAO.getTransactions(
            userEmail, typeFilter, categoryFilter, dateFrom, dateTo, keyword
        );

        request.setAttribute("transactions", transactions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("transaction-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Transaction transaction = transactionDAO.getTransactionById(id);
        request.setAttribute("transaction", transaction);
        RequestDispatcher dispatcher = request.getRequestDispatcher("transaction-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTransaction(HttpServletRequest request, HttpServletResponse response, String userEmail)
            throws SQLException, IOException {
        Transaction transaction = new Transaction();
        transaction.setUserEmail(userEmail);
        transaction.setTitle(request.getParameter("title"));
        transaction.setAmount(new BigDecimal(request.getParameter("amount")));
        transaction.setType(request.getParameter("type"));
        transaction.setCategory(request.getParameter("category"));
        transaction.setDate(Date.valueOf(request.getParameter("date")));
        transaction.setNotes(request.getParameter("notes"));

        transactionDAO.addTransaction(transaction);
        response.sendRedirect("transactions");
    }

    private void updateTransaction(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setTitle(request.getParameter("title"));
        transaction.setAmount(new BigDecimal(request.getParameter("amount")));
        transaction.setType(request.getParameter("type"));
        transaction.setCategory(request.getParameter("category"));
        transaction.setDate(Date.valueOf(request.getParameter("date")));
        transaction.setNotes(request.getParameter("notes"));

        transactionDAO.updateTransaction(transaction);
        response.sendRedirect("transactions");
    }

    private void deleteTransaction(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        transactionDAO.deleteTransaction(id);
        response.sendRedirect("transactions");
    }
}