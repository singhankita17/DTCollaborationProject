package com.linkin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
