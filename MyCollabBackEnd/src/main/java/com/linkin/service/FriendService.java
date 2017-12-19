package com.linkin.service;

import java.util.List;

import com.linkin.model.UsersDetails;

public interface FriendService {

	public List<UsersDetails>	listOfSuggestedUsers(int userId);
	  
	  public boolean processFriendRequest(int fromId,int toId);
}
