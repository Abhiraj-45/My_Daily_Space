package com.userPortal.dao;

import com.userPortal.model.Goal;
import com.userPortal.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {
    private Connection connection;

    public GoalDAO() {
        this.connection = DBUtil.getConnection();
    }

    // 1. Add a new goal
    public boolean addGoal(Goal goal) {
        String sql = "INSERT INTO goals (user_email, title, description, category, target_date, priority, progress) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, goal.getUserEmail());
            stmt.setString(2, goal.getTitle());
            stmt.setString(3, goal.getDescription());
            stmt.setString(4, goal.getCategory());
            stmt.setDate(5, new java.sql.Date(goal.getTargetDate().getTime()));
            stmt.setString(6, goal.getPriority());
            stmt.setInt(7, goal.getProgress());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Get all goals for a user
    public List<Goal> getGoalsByUser(String userEmail) {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE user_email = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Goal goal = new Goal();
                goal.setGoalId(rs.getInt("goal_id"));
                goal.setUserEmail(rs.getString("user_email"));
                goal.setTitle(rs.getString("title"));
                goal.setDescription(rs.getString("description"));
                goal.setCategory(rs.getString("category"));
                goal.setTargetDate(rs.getDate("target_date"));
                goal.setPriority(rs.getString("priority"));
                goal.setProgress(rs.getInt("progress"));
                
                goals.add(goal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goals;
    }

    // 3. Update goal progress
    public boolean updateProgress(int goalId, int progress) {
        String sql = "UPDATE goals SET progress = ? WHERE goal_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, progress);
            stmt.setInt(2, goalId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Delete a goal
    public boolean deleteGoal(int goalId) {
        String sql = "DELETE FROM goals WHERE goal_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, goalId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Get goal by ID
    public Goal getGoalById(int goalId) {
        String sql = "SELECT * FROM goals WHERE goal_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, goalId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Goal goal = new Goal();
                goal.setGoalId(rs.getInt("goal_id"));
                goal.setUserEmail(rs.getString("user_email"));
                // Set other fields...
                return goal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateGoal(Goal goal) {
        String sql = "UPDATE goals SET title=?, description=?, category=?, target_date=?, priority=?, progress=? WHERE goal_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, goal.getTitle());
            stmt.setString(2, goal.getDescription());
            stmt.setString(3, goal.getCategory());
            stmt.setDate(4, new java.sql.Date(goal.getTargetDate().getTime()));
            stmt.setString(5, goal.getPriority());
            stmt.setInt(6, goal.getProgress());
            stmt.setInt(7, goal.getGoalId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}