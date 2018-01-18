package com.linkin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;
import com.linkin.service.FriendService;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class FriendRESTController {

	@Autowired
	FriendService friendService;
	
	@Autowired
	UsersService usersService;

		@RequestMapping(value="/suggestedUsersList",method=RequestMethod.GET)
		public ResponseEntity<?> suggestedUsersList(HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
			else {
				
				List<UsersDetails> suggestedUserList = friendService.listOfSuggestedUsers(userId);
				if(suggestedUserList!=null){
					return new ResponseEntity<List<UsersDetails>>(suggestedUserList,HttpStatus.OK);
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(41,"Error in fetching suggested user list"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		
		@RequestMapping(value="/friendRequest/{toId}",method=RequestMethod.GET)
		public ResponseEntity<?> processFriendRequest(@PathVariable("toId") int toId,HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
			else {
				
				if(friendService.processFriendRequest(userId, toId)){
					
					UsersDetails addedFriend = usersService.getUserById(toId);
					
					return new ResponseEntity<UsersDetails>(addedFriend,HttpStatus.OK);
					
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(42,"Error in processing friend request"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		
		@RequestMapping(value="/pendingFriendRequest",method=RequestMethod.GET)
		public ResponseEntity<?> pendingFriendRequest(HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
			else {
				
				List<Friend> pendingRequestList = friendService.listOfPendingRequests(userId);
				if(pendingRequestList!=null){
					
					return new ResponseEntity<List<Friend>>(pendingRequestList,HttpStatus.OK);
					
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(43,"Error in fetching pending friend request list"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		
		@RequestMapping(value="/updateFriendRequest/{id}/{status}",method=RequestMethod.GET)
		public ResponseEntity<?> updateFriendRequest(@PathVariable("id") int fromId,@PathVariable("status")String status,HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
			else {
			
				boolean result = false;
				try
				{
				
				if(status.equals("ACCEPTED")){
					result = friendService.approveFriendRequest(fromId, userId);
				}else if(status.equals("REJECTED")){
					result = friendService.rejectFriendRequest(fromId, userId);
				}
				}catch(Exception e){
					System.out.println("Error msg"+e.getMessage());
				}
				if(result){
					UsersDetails user = usersService.getUserById(fromId);
					return new ResponseEntity<UsersDetails>(user,HttpStatus.OK);
					
				}else{	
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(44,"Error in updating friend request"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		
		@RequestMapping(value="/listOfFriends",method=RequestMethod.GET)
		public ResponseEntity<?> listOfFriends(HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	  
			else {
				
				List<Friend> friendList = friendService.listOfFriends(userId);
				
				if(friendList!=null){
					
					return new ResponseEntity<List<Friend>>(friendList,HttpStatus.OK);
					
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(44,"Error in fetching friend list of user"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		
		@RequestMapping(value="/unfriend/{friendId}",method=RequestMethod.GET)
		public ResponseEntity<?> deleteFriend(@PathVariable("friendId") int friendId,HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	  
			else {
				
				Friend friend = friendService.getFriendById(friendId);
				
				if(friend!=null){
					
					List<Friend> friendList = friendService.deleteFriend(friend, userId);
					
					return new ResponseEntity<List<Friend>>(friendList,HttpStatus.OK);
					
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(46,"Error in deleting friend list of user"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		
		/*@RequestMapping(value="/getNameOfFriends",method=RequestMethod.POST)
		public ResponseEntity<?> getNameOfFriends(@RequestBody ArrayList<Friend> list,HttpSession session){
			
			Integer userId = (Integer) session.getAttribute("userId");
			
			if(userId == null){
				  
				    return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
			else {
			   List<Integer> userList = new ArrayList<Integer>();
			   Map<Integer, String> userNames = null;
				System.out.println("list = "+list);
				if(list == null){
					
					return new ResponseEntity<Map<Integer,String>>(userNames,HttpStatus.OK);
				
				}
				for(Friend friend:list){
					
					int userId1 = friend.getFromId();
					if(!userList.contains(userId1)){
						userList.add(userId1);
					}
					int userId2 = friend.getToId();
					if(!userList.contains(userId2)){
						userList.add(userId2);
					}
					
				}	
				if(userList!=null){
					userNames = usersService.getUsersFullNames(userList);				
				}
					if(userNames!=null){
						
						return new ResponseEntity<Map<Integer,String>>(userNames,HttpStatus.OK);
						
					}else{
						
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(44,"Error in fetching friend list of user"),HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}				
		}*/
}
