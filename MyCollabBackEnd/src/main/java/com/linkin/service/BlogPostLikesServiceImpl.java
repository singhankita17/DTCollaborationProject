package com.linkin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.BlogPostLikesDao;
import com.linkin.model.Blog;
import com.linkin.model.BlogPostLikes;
import com.linkin.model.UsersDetails;

@Service("blogPostLikesService")
public class BlogPostLikesServiceImpl implements BlogPostLikesService {

	@Autowired
	BlogPostLikesDao blogPostLikesDao;
	
	public BlogPostLikes getUserBlogPostLikes(Blog blog, UsersDetails user) {
		
		return blogPostLikesDao.getUserBlogPostLikes(blog, user);
	}

	public Blog updateBlogPostLikes(Blog blog, UsersDetails user) {
		
		return blogPostLikesDao.updateBlogPostLikes(blog, user);
	}

}
