package com.linkin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.Blog;
import com.linkin.model.BlogComment;
import com.linkin.model.Forum;
import com.linkin.model.ForumComment;
import com.linkin.model.UsersDetails;
import com.linkin.service.ForumCommentService;
import com.linkin.service.ForumService;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class ForumRESTController {

	@Autowired
	UsersService userService;
	
	@Autowired
	ForumService forumService;
	
	@Autowired
	ForumCommentService forumCommentService;
	
	@RequestMapping(value="/createForum",method=RequestMethod.POST)
	public ResponseEntity<?> createForum(@RequestBody Forum forum,HttpSession session){
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	UsersDetails user = userService.getUserById(userId);
		    	if(user.isOnline()){
		    		forum.setUserId(userId);
		    		forum.setStatus("PENDING");
			    	forum.setCreatedDate(new Date());
					if(forumService.addForum(forum)){
						return new ResponseEntity<Forum>(forum, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(11,"Forum Creation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
		    	}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to create Forum"),HttpStatus.CONFLICT);
	}
	
	
	@RequestMapping(value="/updateForum",method=RequestMethod.PUT)
	public ResponseEntity<?> updateForum(@RequestBody Forum forum,HttpSession session){
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	UsersDetails user = userService.getUserById(userId);
		    	Forum forumObj = forumService.getForum(forum.getForumId()); 
		    	if(user.isOnline()){
		    		forumObj.setForumName(forum.getForumName());
		    		forumObj.setForumContent(forum.getForumContent());
		    		forumObj.setStatus("PENDING");
					if(forumService.updateForum(forum)){
						return new ResponseEntity<Forum>(forum, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(11,"Forum Updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
		    	}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to update Forum"),HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value="/deleteForumById/{forumId}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteForumById(@PathVariable("forumId") int forumId,HttpSession session){
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	UsersDetails user = userService.getUserById(userId);
		    	
		    	if(user.isOnline()){
		    		Forum forumObj = forumService.getForum(forumId); 
					if(forumService.deleteForum(forumObj)){
						return new ResponseEntity<Forum>(forumObj, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Forum Deletion failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
		    	}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to update Forum"),HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value="/viewForums",method=RequestMethod.GET)
	public ResponseEntity<?> viewForums(){
		List<Forum> forumList = forumService.getAllUsersForum();
		if(forumList!=null){
				return new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Forum details failed"), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value="/viewForumById/{forumId}",method=RequestMethod.GET)
	public ResponseEntity<?> viewForumById(@PathVariable("forumId") int forumId){
		Forum forumObj = forumService.getForum(forumId);
		if(forumObj!=null){
				return new ResponseEntity<Forum>(forumObj, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Forum details failed"), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/viewPendingForums",method=RequestMethod.GET)
	public ResponseEntity<?> viewPendingForums(){
		List<Forum> forumList = forumService.getAllPendingForums();
		if(forumList!=null){
				return new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Forum details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/viewUserForums",method=RequestMethod.GET)
	public ResponseEntity<?> viewUserForums(HttpSession session){
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
				List<Forum> forumList = forumService.getAllForumsOfUser(userId);
				if(forumList!=null){
						return new ResponseEntity<List<Forum>>(forumList, HttpStatus.OK);
				}
				else
				{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Forum details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
		    }
	}
	
	@RequestMapping(value="/getForumComment/{forumId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogComment(@PathVariable("forumId") int forumId){
		
		List<ForumComment> commentList = forumCommentService.getAllForumComments(forumId);
		
		if(commentList!=null){
			
				return new ResponseEntity<List<ForumComment>>(commentList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(35,"Retrieving Forum Comment details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/addForumComment",method=RequestMethod.POST)
	public ResponseEntity<?> addForumComment(@RequestBody ForumComment forumComment,HttpSession session){
		System.out.println("Forum comment = "+forumComment.getForumId() );
		Integer userId = (Integer) session.getAttribute("userId");
		
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	UsersDetails user = userService.getUserById(userId);
		    	if(user.isOnline()){
		    				forumComment.setCommentDate(new Date());
		    				forumComment.setUserName(user.getFirstName()+" "+user.getLastName());
			if(forumCommentService.addForumComment(forumComment)){
				return new ResponseEntity<ForumComment>(forumComment, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(34,"Forum Comment Addition failed"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to add a comment"),HttpStatus.CONFLICT);
		  
		}
	}
}
