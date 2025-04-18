package com.userPortal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.userPortal.model.Notes;
import com.userPortal.util.DBUtil;

public class NotesDAO {

	
	public boolean addNote(Notes notes) {
		boolean success = false ;
		
		try {
			Connection conn = DBUtil.getConnection();
			String query = "INSERT INTO notes(user_email,title,content)VALUES(?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, notes.getUserEmail());
			ps.setString(2, notes.getTitle());
			ps.setString(3, notes.getContent());
			
			int rows = ps.executeUpdate();
			if(rows>0) {
				success = true ;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success; 
	}
	
	public List<Notes>getNotesByUser(String email){
		List<Notes>notesList = new ArrayList<>();
		try {
				Connection conn = DBUtil.getConnection();
				String query = "SELECT * FROM notes WHERE user_email=? ORDER BY created_at DESC";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					Notes notes = new Notes();
					notes.setId(rs.getInt("id"));
					notes.setUserEmail(rs.getString("user_email"));
					notes.setTitle(rs.getString("title"));
					notes.setContent(rs.getString("content"));
					notes.setCreatedAt(rs.getTimestamp("created_at"));
					notesList.add(notes);
					
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		return notesList;
	}
	
	public boolean deleteNote(int id) {
		boolean deleted = false ;
		
		try {
			Connection conn = DBUtil.getConnection();
			String query = "DELETE FROM notes WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			int rows = ps.executeUpdate();
			if(rows>0) {
				deleted = true ;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return deleted ;
	}
}
