package com.linkin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;

@Repository
public class FriendDaoImpl implements FriendDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Transactional
	public List<UsersDetails> listOfSuggestedUsers(int userId) {
		
		Session session = sessionFactory.openSession();
		
		
		SQLQuery query= session.createSQLQuery("select * from c_users where c_user_id IN (select c_user_id from c_users WHERE c_user_id != ? " 
				+ "MINUS (select fromId from friends where toId = ? "
				+ "UNION select toId from friends where fromId = ?))");
		query.setInteger(0, userId);
		query.setInteger(1, userId);
		query.setInteger(2, userId);
		query.addEntity(UsersDetails.class);
		List<UsersDetails> suggestedList = query.list();
		session.close();
		return suggestedList;
	}

	@Transactional
	public boolean processFriendRequest(int fromId, int toId) {
	
		Friend friend = new Friend();
		friend.setFromId(fromId);
		friend.setToId(toId);
		friend.setStatus("PENDING");
		try{
			sessionFactory.getCurrentSession().save(friend);
			return true;
		}catch(Exception e){
			System.out.println("Error = "+e.getMessage());
			return false;
		}
		
	}

}
