package com.linkin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.BlogComment;
import com.linkin.model.ForumComment;

@Repository
public class ForumCommentDaoImpl implements ForumCommentDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public boolean addForumComment(ForumComment comment) {
		try
		{
		    sessionFactory.getCurrentSession().save(comment);
			return true;
			
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean updateForumComment(ForumComment comment) {
		try{
			sessionFactory.getCurrentSession().update(comment);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean deleteForumComment(ForumComment comment) {
		try
		{
			sessionFactory.getCurrentSession().delete(comment);
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public ForumComment getForumComment(int forumCommentId) {
		
		Session session = sessionFactory.openSession();
		
		ForumComment forumObj = session.get(ForumComment.class, forumCommentId);
		
		session.close();
		
		return forumObj;
	}

	@Transactional
	public List<ForumComment> getAllForumComments(int forumId) {

		Session session = sessionFactory.openSession();
		
		List<ForumComment> forumCommentList= session.createQuery("from ForumComment",ForumComment.class).list();
		
		session.close();
		
		return forumCommentList;
		
	}

}
