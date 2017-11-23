package com.linkin.dao;

import java.util.List;

import com.linkin.model.UsersDetails;


public interface UsersDao {

	public boolean saveOrUpdate(UsersDetails user);
	
	public boolean deleteUser(UsersDetails user);
	
	public UsersDetails getUserById(int userId);
	
	public UsersDetails getUserByEmail(String email);
	
	public UsersDetails getUserByName(String name);
	
	public List<UsersDetails> getAllUsers();

	public boolean checkIfExistingUser(UsersDetails user);

	public boolean checkIfValidUser(UsersDetails user);
	
}
