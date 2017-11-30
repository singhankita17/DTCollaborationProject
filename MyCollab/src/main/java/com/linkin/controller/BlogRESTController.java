package com.linkin.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.Blog;
import com.linkin.model.UsersDetails;
import com.linkin.service.BlogService;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class BlogRESTController {
	
	@Autowired
	UsersService userService;
	
	@Autowired
	BlogService blogService;

	@RequestMapping(value="/createBlog",method=RequestMethod.POST)
	public ResponseEntity<?> createBlog(@RequestBody Blog blog){
		UsersDetails user = userService.getUserById(blog.getUserId());
		if(user.isOnline()){
			blog.setStatus("PENDING");
			blog.setCreatedDate(new Date());
			blog.setNoOfDislikes(0);
			blog.setNoOfDislikes(0);
			if(blogService.addBlog(blog)){
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}else{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(11,"Blog Creation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to create blog"),HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value="/viewBlogs",method=RequestMethod.GET)
	public ResponseEntity<?> viewBlogs(){
		List<Blog> blogList = blogService.getAllUsersBlog();
		if(blogList!=null){
				return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/viewPendingBlogs",method=RequestMethod.GET)
	public ResponseEntity<?> viewPendingBlogs(){
		List<Blog> blogList = blogService.getAllPendingBlogs();
		if(blogList!=null){
				return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/approveBlog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> approveBlog(@PathVariable("blogId") int blogId){
		Blog blog = blogService.getBlog(blogId);
		if(blog!=null){
				
			if(blogService.approveBlog(blog)){
				    blog = blogService.getBlog(blogId);
					return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}
			else
			{
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(14,"Error occured during approving Blog details"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(15,"No Blogs found for given Id"), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/rejectBlog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> rejectBlog(@PathVariable("blogId") int blogId){
		Blog blog = blogService.getBlog(blogId);
		
		if(blog!=null){
			
			if(blogService.rejectBlog(blog)){
					blog = blogService.getBlog(blogId);
					return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}
			else
			{
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(14,"Error occured during rejecting Blog details"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(15,"No Blogs found for given Id"), HttpStatus.NOT_FOUND);
		}
	}
	
}