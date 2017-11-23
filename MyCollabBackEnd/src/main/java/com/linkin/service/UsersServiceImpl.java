package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.UsersDao;
import com.linkin.model.UsersDetails;

@Service("usersService")
public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersDao usersDao;

	public boolean saveOrUpdate(UsersDetails user) {
		
		return usersDao.saveOrUpdate(user);
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

	public boolean checkIfValidUser(UsersDetails user) {
		
		return usersDao.checkIfValidUser(user);
	}

	public boolean deleteUser(UsersDetails user) {
		
		return usersDao.deleteUser(user);
	}
	
	
}
