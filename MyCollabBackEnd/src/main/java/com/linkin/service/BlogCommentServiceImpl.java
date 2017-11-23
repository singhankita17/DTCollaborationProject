package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.BlogCommentDao;
import com.linkin.model.BlogComment;

@Service("blogCommentService")
public class BlogCommentServiceImpl implements BlogCommentService {

	
	@Autowired
	BlogCommentDao blogCommentDao;
	
	public boolean addBlogComment(BlogComment comment) {
		
		return blogCommentDao.addBlogComment(comment);
	}

	public boolean updateBlogComment(BlogComment comment) {
		
		return blogCommentDao.updateBlogComment(comment);
	}

	public boolean deleteBlogComment(BlogComment comment) {
		
		return blogCommentDao.deleteBlogComment(comment);
	}

	public BlogComment getBlogComment(int blogCommentId) {
		
		return blogCommentDao.getBlogComment(blogCommentId);
	}

	public List<BlogComment> getAllBlogComments(int blogId) {
		
		return blogCommentDao.getAllBlogComments(blogId);
	}

}
