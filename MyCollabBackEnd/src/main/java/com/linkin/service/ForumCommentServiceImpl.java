package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.ForumCommentDao;
import com.linkin.model.ForumComment;

@Service("forumCommentService")
public class ForumCommentServiceImpl implements ForumCommentService {
	
	@Autowired
	ForumCommentDao forumCommentDao;

	public boolean addForumComment(ForumComment comment) {
		
		return forumCommentDao.addForumComment(comment);
	}

	public boolean updateForumComment(ForumComment comment) {
		
		return forumCommentDao.updateForumComment(comment);
	}

	public boolean deleteForumComment(ForumComment comment) {
		
		return forumCommentDao.deleteForumComment(comment);
	}

	public ForumComment getForumComment(int forumCommentId) {
		
		return forumCommentDao.getForumComment(forumCommentId);
	}

	public List<ForumComment> getAllForumComments(int forumId) {
		
		return forumCommentDao.getAllForumComments(forumId);
	}

}
