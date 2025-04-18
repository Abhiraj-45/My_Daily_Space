package com.userPortal.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {

	private int id ;
	private String userEmail ;
	private String title ;
	private BigDecimal amount ;
	private String type ;
	private String category ;
	private Date date ;
	private String notes ;
	
	public Transaction() {}
	
	public Transaction(int id ,String userEmail ,String title, BigDecimal amount,  String type, String category ,Date date, String notes) {
		this.id = id;
        this.userEmail = userEmail;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.notes = notes;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
    public String toString() {
        return "Transaction [id=" + id + ", title=" + title + ", amount=" + amount + 
               ", type=" + type + ", category=" + category + "]";
    }

	
}
