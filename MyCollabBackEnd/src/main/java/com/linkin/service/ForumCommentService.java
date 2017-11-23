package com.linkin.service;

import java.util.List;

import com.linkin.model.ForumComment;

public interface ForumCommentService {

	public boolean addForumComment(ForumComment comment);
	
	public boolean updateForumComment(ForumComment comment);
	
	public boolean deleteForumComment(ForumComment comment);
	
	public ForumComment getForumComment(int forumCommentId);
	
	public List<ForumComment> getAllForumComments(int forumId);
}
