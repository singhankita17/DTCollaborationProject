package com.linkin.service;

import java.util.List;

import com.linkin.model.Forum;

public interface ForumService {
	
	public boolean addForum(Forum forum);
	
	public boolean updateForum(Forum forum);
	
	public boolean deleteForum(Forum forum);
	
	public Forum getForum(int forumId);
	
	public List<Forum> getAllForumsOfUser(int userId);
	
	public boolean approveForum(Forum forum);
	
	public List<Forum> getAllUsersForum();

	public List<Forum> getAllPendingForums();
}
