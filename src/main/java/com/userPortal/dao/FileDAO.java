package com.userPortal.dao;

import com.userPortal.model.UserFile;
import com.userPortal.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
    private Connection connection;

    public FileDAO() {
        this.connection = DBUtil.getConnection();
    }

    public boolean saveFile(UserFile file) throws SQLException {
        String sql = "INSERT INTO user_files (user_email, original_name, stored_name, " +
                     "file_path, file_size, file_type, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, file.getUserEmail());
            stmt.setString(2, file.getOriginalName());
            stmt.setString(3, file.getStoredName());
            stmt.setString(4, file.getFilePath());
            stmt.setLong(5, file.getFileSize());
            stmt.setString(6, file.getFileType());
            stmt.setString(7, file.getDescription());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        file.setFileId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public UserFile getFileById(int fileId) throws SQLException {
        String sql = "SELECT * FROM user_files WHERE file_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fileId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new UserFile(
                    rs.getInt("file_id"),
                    rs.getString("user_email"),
                    rs.getString("original_name"),
                    rs.getString("stored_name"),
                    rs.getString("file_path"),
                    rs.getLong("file_size"),
                    rs.getString("file_type"),
                    rs.getTimestamp("upload_date"),
                    rs.getString("description")
                );
            }
            return null;
        }
    }

    public List<UserFile> getUserFiles(String userEmail) throws SQLException {
        List<UserFile> files = new ArrayList<>();
        String sql = "SELECT * FROM user_files WHERE user_email = ? ORDER BY upload_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                files.add(new UserFile(
                    rs.getInt("file_id"),
                    rs.getString("user_email"),
                    rs.getString("original_name"),
                    rs.getString("stored_name"),
                    rs.getString("file_path"),
                    rs.getLong("file_size"),
                    rs.getString("file_type"),
                    rs.getTimestamp("upload_date"),
                    rs.getString("description")
                ));
            }
        }
        return files;
    }

    public boolean deleteFile(int fileId) throws SQLException {
        String sql = "DELETE FROM user_files WHERE file_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fileId);
            return stmt.executeUpdate() > 0;
        }
    }
}