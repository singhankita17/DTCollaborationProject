package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.BlogDao;
import com.linkin.model.Blog;

@Service("blogService")
public class BlogServiceImpl implements BlogService{

	@Autowired
	BlogDao blogDao;
	
	public boolean addBlog(Blog blog) {
		return blogDao.addBlog(blog);
		
	}

	public boolean updateBlog(Blog blog) {
		return blogDao.updateBlog(blog);
		
	}

	public Blog getBlog(int blogId) {
		return blogDao.getBlog(blogId);
	}

	public List<Blog> getAllBlogs(int userId) {
		return blogDao.getAllBlogs(userId);
	}

	public boolean approveBlog(Blog blog) {
	
		return blogDao.approveBlog(blog);
		
	}

	public List<Blog> getAllUsersBlog() {
		
		return blogDao.getAllUsersBlog();
	}

}
