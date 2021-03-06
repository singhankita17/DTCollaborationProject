package com.linkin.service;

import java.util.List;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;

public interface FriendService {

	public List<UsersDetails>	listOfSuggestedUsers(int userId);
	  
	  public boolean processFriendRequest(int fromId,int toId);
	  
	  public List<Friend> listOfPendingRequests(int userId);
	  
	  public boolean approveFriendRequest(int fromId,int toId);
	  
	  public boolean rejectFriendRequest(int fromId,int toId);
	  
	  public List<Friend> listOfFriends(int userId);
	  
	  public List<Friend> deleteFriend(Friend friend,int userId);
	  
	  public Friend getFriendById(int friendId);
}
