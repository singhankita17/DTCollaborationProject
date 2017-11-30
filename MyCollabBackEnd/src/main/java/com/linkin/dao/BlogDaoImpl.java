package com.linkin.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.Blog;

@Repository
public class BlogDaoImpl implements BlogDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public boolean addBlog(Blog blog) {
		try
		{
		    sessionFactory.getCurrentSession().save(blog);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean updateBlog(Blog blog) {
		try{
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}

	}

	@Transactional
	public Blog getBlog(int blogId) {
		
		Session session = sessionFactory.openSession();
			
		Blog blogObj = session.get(Blog.class, blogId);
		
		session.close();
		
		return blogObj;
	}

	@Transactional
	public List<Blog> getAllBlogs(int userId) {
		
		Session session = sessionFactory.openSession();
		
		List<Blog> blogList= session.createQuery("from Blog where userId = :userId",Blog.class)
				.setParameter("userId", userId).list();
		
		session.close();
		
		return blogList;
	}

	@Transactional
	public boolean approveBlog(Blog blog) {
		try{
			
			Blog blogObj = sessionFactory.getCurrentSession().load(Blog.class, blog.getBlogId());
			blogObj.setStatus("APPROVED");
			blogObj.setPublishDate(new Date());
			return true;
			
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}

	}

	@Transactional
	public boolean deleteBlog(Blog blog) {
		try
		{
			sessionFactory.getCurrentSession().delete(blog);
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public List<Blog> getAllUsersBlog() {
		Session session = sessionFactory.openSession();
		
		List<Blog> blogList= session.createQuery("from Blog where status = 'APPROVED'",Blog.class).list();
		
		session.close();
		
		return blogList;
	}

	@Transactional
	public boolean rejectBlog(Blog blog) {
		try{
			
			Blog blogObj = sessionFactory.getCurrentSession().load(Blog.class, blog.getBlogId());
			blogObj.setStatus("REJECTED");
			blogObj.setPublishDate(new Date());
			return true;
			
		  }catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		  }
	}

	@Transactional
	public List<Blog> getAllPendingBlogs() {

		Session session = sessionFactory.openSession();
		
		List<Blog> blogList= session.createQuery("from Blog where status = 'PENDING'",Blog.class).list();
		
		session.close();
		
		return blogList;
	}

	@Transactional
	public int incrementLikes(int blogId) {
		try{
			Blog blogObj = sessionFactory.getCurrentSession().load(Blog.class, blogId);
			int noOfLikes = blogObj.getNoOfLikes();
			noOfLikes++;
			blogObj.setNoOfLikes(noOfLikes);
			return noOfLikes;
			
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return 0;
		}
	}

	@Transactional
	public int incrementDisLikes(int blogId) {
		try{
			
			Blog blogObj = sessionFactory.getCurrentSession().load(Blog.class, blogId);
			int noOfDislikes = blogObj.getNoOfDislikes();
			noOfDislikes++;
			blogObj.setNoOfLikes(noOfDislikes);
			return noOfDislikes;
			
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return 0;
		}
	}

}
