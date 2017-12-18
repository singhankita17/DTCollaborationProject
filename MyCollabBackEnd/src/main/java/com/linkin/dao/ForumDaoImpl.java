package com.linkin.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.Blog;
import com.linkin.model.Forum;

@Repository
public class ForumDaoImpl implements ForumDao {

	@Autowired
	SessionFactory sessionFactory;
	
	
	@Transactional
	public boolean addForum(Forum forum) {
		try
		{
		    sessionFactory.getCurrentSession().save(forum);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean updateForum(Forum forum) {
		try{
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean deleteForum(Forum forum) {
		try
		{
			sessionFactory.getCurrentSession().delete(forum);
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public Forum getForum(int forumId) {
		
		Session session = sessionFactory.openSession();
		
		Forum forumObj = session.get(Forum.class, forumId);
		
		session.close();
		
		return forumObj;
	}

	@Transactional
	public List<Forum> getAllForumsOfUser(int userId) {
		
		Session session = sessionFactory.openSession();
		
		List<Forum> forumList= session.createQuery("from Forum where userId = :userId",Forum.class)
				.setParameter("userId", userId).list();
		
		session.close();
		
		return forumList;
	}

	@Transactional
	public boolean approveForum(Forum forum) {
		try{			
		    Forum forumObj = sessionFactory.getCurrentSession().load(Forum.class, forum.getForumId());
		    forumObj.setStatus("APPROVED");
		    forumObj.setPublishDate(new Date());
		    return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public List<Forum> getAllUsersForum() {
		Session session = sessionFactory.openSession();
		
		List<Forum> forumList= session.createQuery("from Forum where status = 'APPROVED'",Forum.class).list();
		
		session.close();
		
		return forumList;
	}

	@Transactional
	public List<Forum> getAllPendingForums() {
		
		Session session = sessionFactory.openSession();
		
		List<Forum> forumList= session.createQuery("from Forum where status = 'PENDING'",Forum.class).list();
		
		session.close();
		
		return forumList;
	}

	@Transactional
	public boolean rejectForum(Forum forum) {
		try{			
		    Forum forumObj = sessionFactory.getCurrentSession().load(Forum.class, forum.getForumId());
		    forumObj.setStatus("REJECTED");
		    forumObj.setPublishDate(new Date());
		    return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

}
