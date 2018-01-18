package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.FriendDao;
import com.linkin.model.Friend;
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

	public List<Friend> listOfPendingRequests(int userId) {
		
		return friendDao.listOfPendingRequests(userId);
	}

	public boolean approveFriendRequest(int fromId, int toId) {
		
		return friendDao.approveFriendRequest(fromId, toId);
	}

	public boolean rejectFriendRequest(int fromId, int toId) {
		
		return friendDao.rejectFriendRequest(fromId, toId);
	}

	public List<Friend> listOfFriends(int userId) {
		
		return friendDao.listOfFriends(userId);
	}

	public List<Friend> deleteFriend(Friend friend, int userId) {
		
		friendDao.deleteFriend(friend);
		List<Friend> friendList = friendDao.listOfFriends(userId);
		return friendList;
	}

	public Friend getFriendById(int friendId) {
		
		return friendDao.getFriendById(friendId);
	}

}
