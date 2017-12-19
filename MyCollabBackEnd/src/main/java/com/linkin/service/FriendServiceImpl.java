package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.FriendDao;
import com.linkin.model.UsersDetails;

@Service("friendService")
public class FriendServiceImpl implements FriendService {
	
	@Autowired
	FriendDao friendDao;

	public List<UsersDetails> listOfSuggestedUsers(int userId) {
		
		return friendDao.listOfSuggestedUsers(userId);
		
	}

	public boolean processFriendRequest(int fromId, int toId) {
		
		return friendDao.processFriendRequest(fromId, toId);
	}

}
