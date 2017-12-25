package com.linkin.dao;

import java.util.List;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;

public interface FriendDao {

  public List<UsersDetails>	listOfSuggestedUsers(int userId);
  
  public boolean processFriendRequest(int fromId,int toId);
  
  public List<Friend> listOfPendingRequests(int userId);
  
  public boolean approveFriendRequest(int fromId,int toId);
  
  public boolean rejectFriendRequest(int fromId,int toId);
  
  public List<Friend> listOfFriends(int userId);
}
