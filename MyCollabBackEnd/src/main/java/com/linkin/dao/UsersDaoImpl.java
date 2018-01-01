package com.linkin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;

@Repository
public class UsersDaoImpl implements UsersDao{
	
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public boolean saveUser(UsersDetails user) {
			
		try{
			Session session = sessionFactory.getCurrentSession();
			user.setEnabled(true);
			user.setOnline(false);
			session.save(user);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@Transactional
	public boolean updateUser(UsersDetails user) {
			
		try{
			Session session = sessionFactory.getCurrentSession();
			session.update(user);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Transactional
	public UsersDetails getUserById(int userId) {
		
		String queryString = "from UsersDetails where c_user_id = :userId";
		UsersDetails userObj = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class).setParameter("userId",userId).uniqueResult();
		return userObj;
	}

	@Transactional
	public UsersDetails getUserByEmail(String email) {
		String queryString = "from UsersDetails where email = :email";
		UsersDetails userObj = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class).setParameter("email",email).uniqueResult();
		return userObj;
	}

	@Transactional
	public List<UsersDetails> getAllUsers() {
		String queryString = "from UsersDetails";
		List<UsersDetails> userList = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class).list();
		return userList;
	}

	@Transactional
	public boolean checkIfExistingUser(UsersDetails user) {
		
		String queryString = "from UsersDetails where email = :userEmail OR userName = :username";
		UsersDetails userObj = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class).setParameter("userEmail",user.getEmail())
				.setParameter("username", user.getUserName()).uniqueResult();
		if(userObj!=null)
				return true;
		else
				return false;
	}

	@Transactional
	public UsersDetails getUserByName(String name) {
		String queryString = "from UsersDetails where username = :name";
		UsersDetails userObj = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class).setParameter("name",name).uniqueResult();
		return userObj;
	}

	@Transactional
	public boolean checkIfValidUser(UsersDetails user) {
		return false;
	}

	@Transactional
	public boolean deleteUser(int id) {
		try
		{
			Session session = sessionFactory.getCurrentSession();
			UsersDetails user = session.get(UsersDetails.class, id);
			session.delete(user);
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean checkIfValidUser(String username, String password) {
		String queryString = "from UsersDetails where userName = :username AND password = :password";
		UsersDetails userObj = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class)
				.setParameter("username", username)
				.setParameter("password",password).uniqueResult();
		if(userObj!=null)
				return true;
		else
				return false;
	}

	@Transactional
	public Map<Integer, String> getUsersFullNames(List<Integer> friendList) {
		
		String queryString = "select * from c_users where c_user_id IN (";
		
		for(Integer userId:friendList){
			
			queryString += userId+",";
		}
		queryString = queryString.substring(0, queryString.length()-1);
		queryString += ")";
		System.out.println("Query ==="+queryString);
		Session session = sessionFactory.getCurrentSession();
				
		List<UsersDetails> users =  session.createSQLQuery(queryString).addEntity(UsersDetails.class).list();
		Map<Integer,String> userNames = new HashMap<Integer, String>();
		
		for(UsersDetails user:users){
			userNames.put(user.getC_user_id(), user.getFirstName()+" "+user.getLastName());
		}
		return userNames;
	}

	@Transactional
	public List<Integer> getOnlineUserList() {
		
		List<Integer> userList = new ArrayList<Integer>();
		String queryString = "select * from c_users where ISONLINE = 1";
		List<UsersDetails> users = sessionFactory.getCurrentSession().createSQLQuery(queryString).addEntity(UsersDetails.class).list();
		for(UsersDetails user:users){
			userList.add(user.getC_user_id());
		}
		
		return userList;
	}

	@Transactional
	public boolean checkIfAlreadyLoggedInUser(UsersDetails user) {
		String queryString = "from UsersDetails where c_user_id = :userId and ISONLINE = 1";
		UsersDetails userObj = sessionFactory.getCurrentSession().createQuery(queryString,UsersDetails.class)
				.setParameter("userId", user.getC_user_id()).uniqueResult();
		if(userObj!=null)
				return true;
		else
				return false;
	}

	
}
