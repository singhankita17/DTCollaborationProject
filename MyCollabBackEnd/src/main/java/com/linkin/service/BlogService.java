package com.linkin.service;

import java.util.List;

import com.linkin.model.Blog;

public interface BlogService {

	public boolean addBlog(Blog blog);
	
	public boolean updateBlog(Blog blog);
	
	public Blog getBlog(int blogId);
	
	public List<Blog> getAllBlogs(int userId);
	
	public boolean approveBlog(Blog blog);
	
	public boolean rejectBlog(Blog blog);
	
	public List<Blog> getAllBlogs();	
	
	public boolean deleteBlog(Blog blog);
	
	public List<Blog> getAllPendingBlogs();	
	
	public List<Blog> getAllApprovedBlog();

	public int updateNoOfLikes(Blog blog);
	
}
