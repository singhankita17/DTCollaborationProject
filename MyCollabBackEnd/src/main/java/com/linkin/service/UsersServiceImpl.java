package com.linkin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.UsersDao;
import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;

@Service("usersService")
public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersDao usersDao;

	public boolean saveUser(UsersDetails user) {
		
		return usersDao.saveUser(user);
	}
	
	public boolean updateUser(UsersDetails user) {
		
		return usersDao.updateUser(user);
	}

	public UsersDetails getUserById(int userId) {
		
		return usersDao.getUserById(userId);
	}

	public UsersDetails getUserByEmail(String email) {
		
		return usersDao.getUserByEmail(email);
	}

	public List<UsersDetails> getAllUsers() {
		
		return usersDao.getAllUsers();
	}

	public boolean checkIfExistingUser(UsersDetails user) {
		
		return usersDao.checkIfExistingUser(user);
	}

	public UsersDetails getUserByName(String name) {
		
		return usersDao.getUserByName(name);
	}

	public boolean deleteUser(int id) {
		
		return usersDao.deleteUser(id);
	}

	public boolean checkIfValidUser(String username, String password) {
		
		return usersDao.checkIfValidUser(username, password);
	}

	public List<Integer> getOnlineUserList() {
		
		return usersDao.getOnlineUserList();
	}

	public boolean checkIfAlreadyLoggedInUser(UsersDetails user) {
		
		return usersDao.checkIfAlreadyLoggedInUser(user);
	}

	public Map<Integer, String> getAllUsersFullNames() {
		
		return usersDao.getAllUsersFullNames();
	}

}
