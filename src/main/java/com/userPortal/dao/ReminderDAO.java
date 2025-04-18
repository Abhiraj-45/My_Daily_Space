package com.userPortal.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.userPortal.model.Reminder;
import com.userPortal.util.DBUtil;

public class ReminderDAO {

    private Connection conn;

    public ReminderDAO() {
        conn = DBUtil.getConnection();
    }

    // Add new reminder
    public boolean addReminder(Reminder reminder) {
        boolean added = false;
        String query = "INSERT INTO reminders (user_email, title, description, reminder_time, created_at) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, reminder.getUserEmail());
            ps.setString(2, reminder.getTitle());
            ps.setString(3, reminder.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(reminder.getReminderTime()));
            ps.setTimestamp(5, Timestamp.valueOf(reminder.getCreatedAt()));

            added = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return added;
    }

    // Get all reminders for a specific user
    public List<Reminder> getReminderByUser(String userEmail) {
        List<Reminder> list = new ArrayList<>();
        String query = "SELECT * FROM reminders WHERE user_email = ? ORDER BY reminder_time ASC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userEmail);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reminder reminder = new Reminder();
                    reminder.setId(rs.getInt("id"));
                    reminder.setUserEmail(rs.getString("user_email"));
                    reminder.setTitle(rs.getString("title"));
                    reminder.setDescription(rs.getString("description"));
                    reminder.setReminderTime(rs.getTimestamp("reminder_time").toLocalDateTime());
                    reminder.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    list.add(reminder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete reminder by ID
    public boolean deleteReminder(int id) {
        String query = "DELETE FROM reminders WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
