package com.linkin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

	
}
