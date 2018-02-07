package com.linkin.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.Blog;
import com.linkin.model.BlogPostLikes;
import com.linkin.model.UsersDetails;

@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public BlogPostLikes getUserBlogPostLikes(Blog blog, UsersDetails user) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from BlogPostLikes where blog.blogId = :blogId and user.c_user_id = :userId",BlogPostLikes.class);
		query.setParameter("blogId", blog.getBlogId());
		query.setParameter("userId", user.getC_user_id());
		BlogPostLikes blogPostLikes = (BlogPostLikes) query.uniqueResult();
		
		return blogPostLikes;
	}

	public Blog updateBlogPostLikes(Blog blog, UsersDetails user) {
		Session session = sessionFactory.getCurrentSession();
		BlogPostLikes blogPostLikes=getUserBlogPostLikes(blog,user);
		
		if(blogPostLikes == null){
			//insert into blogpostlikes, increment blogpost.likes
			BlogPostLikes insertUserLike = new BlogPostLikes();
			insertUserLike.setBlog(blog);
			insertUserLike.setUser(user);
			session.save(insertUserLike); //insert into blogpostlikes
			 //increment the number of likes
			blog.setNoOfLikes(blog.getNoOfLikes() + 1);
			session.update(blog);//update blogpost set likes=.. where id=?
			
		}else{//unlike
			
			session.delete(blogPostLikes);
			//Decrement no of likes
			blog.setNoOfLikes(blog.getNoOfLikes() - 1);
			session.merge(blog); //update blogpost set likes ...
			
		}
		return blog;
	}

}
