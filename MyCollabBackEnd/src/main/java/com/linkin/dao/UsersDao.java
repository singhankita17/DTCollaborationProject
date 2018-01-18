package com.linkin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;


public interface UsersDao {

	public boolean saveUser(UsersDetails user);
	
	public boolean updateUser(UsersDetails user);
	
	public boolean deleteUser(int id);
	
	public UsersDetails getUserById(int userId);
	
	public UsersDetails getUserByEmail(String email);
	
	public UsersDetails getUserByName(String name);
	
	public List<UsersDetails> getAllUsers();

	public boolean checkIfExistingUser(UsersDetails user);

	public boolean checkIfValidUser(String username, String password);
	
	public boolean checkIfAlreadyLoggedInUser(UsersDetails user);
		
	public List<Integer> getOnlineUserList();
	
	public Map<Integer,String> getAllUsersFullNames();
}
