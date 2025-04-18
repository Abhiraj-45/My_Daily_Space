package com.userPortal.model;

import java.sql.Timestamp;

public class UserFile {
	
	private int fileId;
    private String userEmail;
    private String originalName;
    private String storedName;
    private String filePath;
    private long fileSize;
    private String fileType;
    private Timestamp uploadDate;
    private String description;
    
    // Constructors
    public UserFile() {}
    
    public UserFile(int fileId, String userEmail, String originalName, 
                   String storedName, String filePath, long fileSize, 
                   String fileType, Timestamp uploadDate, String description) {
        this.fileId = fileId;
        this.userEmail = userEmail;
        this.originalName = originalName;
        this.storedName = storedName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
        this.description = description;
    }
    
 
	
    public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getStoredName() {
		return storedName;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	  
}