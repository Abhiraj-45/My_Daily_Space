package com.userPortal.dao;

import com.userPortal.model.Transaction;
import com.userPortal.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private final Connection connection;

    public TransactionDAO() {
        this.connection = DBUtil.getConnection();
    }

    // 1. Add new transaction
    public boolean addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_email, title, amount, type, category, date, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transaction.getUserEmail());
            stmt.setString(2, transaction.getTitle());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setString(4, transaction.getType());
            stmt.setString(5, transaction.getCategory());
            stmt.setDate(6, transaction.getDate());
            stmt.setString(7, transaction.getNotes());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        transaction.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    // 2. Get all transactions for a user (with optional filters)
    public List<Transaction> getTransactions(String userEmail, String typeFilter, 
                                           String categoryFilter, String dateFrom, 
                                           String dateTo, String keyword) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM transactions WHERE user_email = ?");
        List<Object> params = new ArrayList<>();
        params.add(userEmail);

        // Dynamic filtering
        if (typeFilter != null && !typeFilter.isEmpty()) {
            sql.append(" AND type = ?");
            params.add(typeFilter);
        }
        if (categoryFilter != null && !categoryFilter.isEmpty()) {
            sql.append(" AND category = ?");
            params.add(categoryFilter);
        }
        if (dateFrom != null && dateTo != null) {
            sql.append(" AND date BETWEEN ? AND ?");
            params.add(Date.valueOf(dateFrom));
            params.add(Date.valueOf(dateTo));
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + keyword + "%");
        }
        sql.append(" ORDER BY date DESC");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserEmail(rs.getString("user_email"));
                t.setTitle(rs.getString("title"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setType(rs.getString("type"));
                t.setCategory(rs.getString("category"));
                t.setDate(rs.getDate("date"));
                t.setNotes(rs.getString("notes"));
                transactions.add(t);
            }
        }
        return transactions;
    }

    // 3. Get transaction by ID
    public Transaction getTransactionById(int id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserEmail(rs.getString("user_email"));
                t.setTitle(rs.getString("title"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setType(rs.getString("type"));
                t.setCategory(rs.getString("category"));
                t.setDate(rs.getDate("date"));
                t.setNotes(rs.getString("notes"));
                return t;
            }
        }
        return null;
    }

    // 4. Update transaction
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET title = ?, amount = ?, type = ?, " +
                     "category = ?, date = ?, notes = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getTitle());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setString(3, transaction.getType());
            stmt.setString(4, transaction.getCategory());
            stmt.setDate(5, transaction.getDate());
            stmt.setString(6, transaction.getNotes());
            stmt.setInt(7, transaction.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // 5. Delete transaction
    public boolean deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 6. Dashboard analytics methods
    public BigDecimal getTotalIncome(String userEmail, String monthYear) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions " +
                     "WHERE user_email = ? AND type = 'INCOME' " +
                     "AND DATE_FORMAT(date, '%Y-%m') = ?";
        return getSum(sql, userEmail, monthYear);
    }

    public BigDecimal getTotalExpense(String userEmail, String monthYear) throws SQLException {
        String sql = "SELECT SUM(amount) FROM transactions " +
                     "WHERE user_email = ? AND type = 'EXPENSE' " +
                     "AND DATE_FORMAT(date, '%Y-%m') = ?";
        return getSum(sql, userEmail, monthYear);
    }

    public List<CategorySummary> getCategorySummary(String userEmail, String type, String monthYear) throws SQLException {
        List<CategorySummary> summary = new ArrayList<>();
        String sql = "SELECT category, SUM(amount) as total FROM transactions " +
                      "WHERE user_email = ? AND type = ? " +
                      "AND DATE_FORMAT(date, '%Y-%m') = ? " +
                      "GROUP BY category";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            stmt.setString(2, type);
            stmt.setString(3, monthYear);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                summary.add(new CategorySummary(
                    rs.getString("category"),
                    rs.getBigDecimal("total")
                ));
            }
        }
        return summary;
    }

    private BigDecimal getSum(String sql, String userEmail, String monthYear) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            stmt.setString(2, monthYear);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal sum = rs.getBigDecimal(1);
                return sum != null ? sum : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        }
    }

    // Helper class for category summary
    public static class CategorySummary {
        private final String category;
        private final BigDecimal total;

        public CategorySummary(String category, BigDecimal total) {
            this.category = category;
            this.total = total;
        }

        // Getters
        public String getCategory() { return category; }
        public BigDecimal getTotal() { return total; }
    }
}