package com.linkin.dao;

import java.util.List;

import com.linkin.model.BlogComment;

public interface BlogCommentDao {
	
	public boolean addBlogComment(BlogComment comment);
	
	public boolean updateBlogComment(BlogComment comment);
	
	public boolean deleteBlogComment(BlogComment comment);
	
	public BlogComment getBlogComment(int blogCommentId);
	
	public List<BlogComment> getAllBlogComments(int blogId);

}
