package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.ForumDao;
import com.linkin.model.Forum;

@Service("forumService")
public class ForumServiceImpl implements ForumService {

	@Autowired
	ForumDao forumDao;
	
	public boolean addForum(Forum forum) {
		
		return forumDao.addForum(forum);
	}

	public boolean updateForum(Forum forum) {
		
		return forumDao.updateForum(forum);
	}

	public boolean deleteForum(Forum forum) {
	
		return forumDao.deleteForum(forum);
	}

	public Forum getForum(int forumId) {
		
		return forumDao.getForum(forumId);
	}

	public List<Forum> getAllForumsOfUser(int userId) {
		
		return forumDao.getAllForumsOfUser(userId);
	}

	public boolean approveForum(Forum forum) {
		
		return forumDao.approveForum(forum);
	}

	public List<Forum> getAllUsersForum() {
		
		return forumDao.getAllUsersForum();
	}

	public List<Forum> getAllPendingForums() {
		
		return forumDao.getAllPendingForums();
	}

}
