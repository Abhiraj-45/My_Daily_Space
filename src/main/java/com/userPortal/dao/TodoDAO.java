package com.userPortal.dao;

import com.userPortal.model.Todo;
import com.userPortal.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    private Connection conn;

    public TodoDAO() {
        conn = DBUtil.getConnection();
    }

    // Add a new todo
    public boolean addTodo(Todo todo) {
        String query = "INSERT INTO todos(user_email, task, status, created_at) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, todo.getUserEmail());
            ps.setString(2, todo.getTask());
            ps.setString(3, todo.getStatus());
            ps.setTimestamp(4, Timestamp.valueOf(todo.getCreatedAt()));

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get todos by user
    public List<Todo> getTodosByUser(String userEmail) {
        List<Todo> list = new ArrayList<>();
        String query = "SELECT * FROM todos WHERE user_email = ? ORDER BY created_at DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setUserEmail(rs.getString("user_email"));
                todo.setTask(rs.getString("task"));
                todo.setStatus(rs.getString("status"));
                todo.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                list.add(todo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update todo status
    public boolean updateTodoStatus(int id, String status) {
        String query = "UPDATE todos SET status = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a todo
    public boolean deleteTodo(int id) {
        String query = "DELETE FROM todos WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
