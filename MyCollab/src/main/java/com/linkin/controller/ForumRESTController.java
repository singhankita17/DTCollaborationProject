package com.linkin.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.Blog;
import com.linkin.model.Forum;
import com.linkin.model.UsersDetails;
import com.linkin.service.ForumService;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class ForumRESTController {

	@Autowired
	UsersService userService;
	
	@Autowired
	ForumService forumService;
	
	@RequestMapping(value="/createForum",method=RequestMethod.POST)
	public ResponseEntity<?> createForum(@RequestBody Forum forum){
		UsersDetails user = userService.getUserById(forum.getUserId());
		if(user.isOnline()){
			forum.setStatus("PENDING");
			forum.setCreatedDate(new Date());
			if(forumService.addForum(forum)){
				return new ResponseEntity<Forum>(forum, HttpStatus.OK);
			}else{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(11,"Forum Creation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to create Forum"),HttpStatus.CONFLICT);
	}
	
	
	
	@RequestMapping(value="/viewForums",method=RequestMethod.GET)
	public ResponseEntity<?> viewBlogs(){
		List<Forum> forumList = forumService.getAllUsersForum();
		if(forumList!=null){
				return new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Forum details failed"), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/viewPendingForums",method=RequestMethod.GET)
	public ResponseEntity<?> viewPendingBlogs(){
		List<Forum> forumList = forumService.getAllPendingForums();
		if(forumList!=null){
				return new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Forum details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
