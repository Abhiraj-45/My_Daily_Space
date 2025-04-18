package com.userPortal.model;

import java.sql.Timestamp;


public class Notes {
	
	private int id;
	private String userEmail ;
	private String title ;
	private String content;
	private Timestamp createdAt ;
	
	 public Notes() {
	    }

	    // Constructor without ID and Timestamp (for creating new notes)
	    public Notes(String userEmail, String title, String content) {
	        this.userEmail = userEmail;
	        this.title = title;
	        this.content = content;
	    }

	    // Constructor with all fields (useful when retrieving from DB)
	    public Notes(int id, String userEmail, String title, String content, Timestamp createdAt) {
	        this.id = id;
	        this.userEmail = userEmail;
	        this.title = title;
	        this.content = content;
	        this.createdAt = createdAt;
	    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
	
}
