package com.linkin.dao;

import java.util.List;

import com.linkin.model.UsersDetails;

public interface FriendDao {

  public List<UsersDetails>	listOfSuggestedUsers(int userId);
  
  public boolean processFriendRequest(int fromId,int toId);
}
