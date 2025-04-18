package com.userPortal.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.userPortal.model.User;
import com.userPortal.util.DBUtil;

public class UserDAO{
	
	public boolean registerUser(User user) {
		boolean status = false ;
		
		
		try {
			
			Connection conn = DBUtil.getConnection();
		    String sql = "INSERT INTO users (name, email, password ) VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			
			
			int rows = ps.executeUpdate();
			if(rows>0) {
				status = true ;
			}
			
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return status ;
	}
	
	
	public boolean validateUser(String email, String password) {
		boolean isValid = false ;
		
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT * FROM users WHERE email=? AND password=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				isValid = true ;
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isValid ;
	}
	
	
	public static boolean changePassword(String email, String oldPass, String newPass) {
	    boolean success = false;
	    try {
	        Connection con = DBUtil.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
	        ps.setString(1, email);
	        ps.setString(2, oldPass);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            ps = con.prepareStatement("UPDATE users SET password=? WHERE email=?");
	            ps.setString(1, newPass);
	            ps.setString(2, email);
	            ps.executeUpdate();
	            success = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return success;
	}

	
	
}