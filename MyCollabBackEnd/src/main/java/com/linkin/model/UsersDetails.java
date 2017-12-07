package com.linkin.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="C_USERS")
public class UsersDetails implements Serializable{
	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int c_user_id;
	private String firstName;
	private String lastName;
	
	@Column(unique=true,nullable=false)
	private String userName;
	private String contact;
	
	@Column(unique=true,nullable=false)
	private String email;
	private String password;
	private String role;
	private boolean isEnabled;
	private boolean isOnline;
	
	@Lob
	private byte[] image;
	
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public int getC_user_id() {
		return c_user_id;
	}
	public void setC_user_id(int c_user_id) {
		this.c_user_id = c_user_id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
}