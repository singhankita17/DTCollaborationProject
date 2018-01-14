package com.linkin.dao;

import java.util.List;

import com.linkin.model.Forum;


public interface ForumDao {
	
	public boolean addForum(Forum forum);
	
	public boolean updateForum(Forum forum);
	
	public boolean deleteForum(Forum forum);
	
	public Forum getForum(int forumId);
	
	public List<Forum> getAllForumsOfUser(int userId);
	
	public boolean approveForum(Forum forum);
	
	public boolean rejectForum(Forum forum);
	
	public List<Forum> getAllUsersForum();

	public List<Forum> getAllPendingForums();

	public List<Forum> getAllForums();

}
