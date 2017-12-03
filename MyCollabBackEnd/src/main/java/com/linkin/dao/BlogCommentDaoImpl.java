package com.linkin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.BlogComment;

@Repository
public class BlogCommentDaoImpl implements BlogCommentDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public boolean addBlogComment(BlogComment comment) {
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
	public boolean updateBlogComment(BlogComment comment) {
		try{
			sessionFactory.getCurrentSession().update(comment);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean deleteBlogComment(BlogComment comment) {
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
	public BlogComment getBlogComment(int blogCommentId) {
		
		Session session = sessionFactory.openSession();
		
		BlogComment blogObj = session.get(BlogComment.class, blogCommentId);
		
		session.close();
		
		return blogObj;
	}

	@Transactional
	public List<BlogComment> getAllBlogComments(int blogId) {
		
		Session session = sessionFactory.openSession();
		
		List<BlogComment> blogCommentList= session.createQuery("from BlogComment where blogId = :blogId",BlogComment.class)
				.setParameter("blogId", blogId).list();
		
		session.close();
		
		return blogCommentList;
		
	}

	
}
