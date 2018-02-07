package com.linkin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.Blog;
import com.linkin.model.BlogComment;
import com.linkin.model.BlogPostLikes;
import com.linkin.model.Notification;
import com.linkin.model.UsersDetails;
import com.linkin.service.BlogCommentService;
import com.linkin.service.BlogPostLikesService;
import com.linkin.service.BlogService;
import com.linkin.service.NotificationService;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class BlogRESTController {
	
	@Autowired
	UsersService userService;
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	BlogCommentService blogCommentService;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	BlogPostLikesService blogPostLikesService;
	
	private static Logger log = LoggerFactory.getLogger(BlogRESTController.class);
	
	@RequestMapping(value="/createBlog",method=RequestMethod.POST)
	public ResponseEntity<?> createBlog(@RequestBody Blog blog,HttpSession session){
		 log.info("Create New Blog: fetch user session details");
		 Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
			 	log.info("Create New Blog: user session details Not Found");
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
				UsersDetails user = userService.getUserById(userId);
				log.info("Create New Blog: user details fetched = "+user.toString());
				if(user.isOnline()){
					log.info("Create New Blog: if user is online create new Blog ");
					blog.setUserId(user.getC_user_id());
					blog.setUserName(user.getUserName());
					blog.setStatus("PENDING");
					blog.setCreatedDate(new Date());
					blog.setNoOfLikes(0);
					if(blogService.addBlog(blog)){
						return new ResponseEntity<Blog>(blog, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(11,"Blog Creation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to create blog"),HttpStatus.CONFLICT);
			}
	}
	
	@RequestMapping(value="/viewApprovedBlogs",method=RequestMethod.GET)
	public ResponseEntity<?> viewApprovedBlogs(){
		 log.info("ViewApprovedBlogs: fetch all Approved Blogs details");
		List<Blog> blogList = blogService.getAllApprovedBlog();
		if(blogList!=null){
				return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value="/viewBlogById/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> viewBlogById(@PathVariable("blogId") int blogId){
		 log.info("ViewBlogById: fetch Blog details by Id = "+blogId);
		Blog blog = blogService.getBlog(blogId);
		 log.info("ViewBlogById:  Blog details by Id = "+blog);
		if(blog!=null){
			 log.info("ViewBlogById: Blog details: "+blog.getBlogId()+" Date: "+blog.getCreatedDate());
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/viewPendingBlogs",method=RequestMethod.GET)
	public ResponseEntity<?> viewPendingBlogs(HttpSession session){
		 log.info("ViewPendingBlogs: fetch all pending Blog details  ");
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
			 log.info("ViewPendingBlogs: user session details not found  ");
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
				List<Blog> blogList = blogService.getAllPendingBlogs();
				 log.info("ViewPendingBlogs: pending Blog List :  "+blogList);
				if(blogList!=null){
						return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
				}
				else
				{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
		    }
	}
	
	@RequestMapping(value="/viewAllBlogs",method=RequestMethod.GET)
	public ResponseEntity<?> viewAllBlogs(){
		log.info("View All Blogs: fetch all Blog details  ");
		List<Blog> blogList = blogService.getAllBlogs();
		log.info("View All Blogs:  Blog details List = "+blogList);
		if(blogList!=null){
				return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value="/viewUserBlogs",method=RequestMethod.GET)
	public ResponseEntity<?> viewUserBlogs(HttpSession session){
		log.info("View User Blogs: fetch all current user Blog details  ");
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId==null)
	    {
	    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
	    else	
	    {
				List<Blog> blogList = blogService.getAllBlogs(userId);
				log.info("View User Blogs: fetch all current user Blog details fetched =  "+blogList);
				if(blogList!=null){
						return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
				}
				else
				{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(13,"Retrieving Blog details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
	    }
	}
	
	@RequestMapping(value="/approveBlog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> approveBlog(@PathVariable("blogId") int blogId,HttpSession session){
		log.info("Approved Blogs: Approve Blog details  ");
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
					Blog blogObj = blogService.getBlog(blogId);
					if(blogObj!=null){				
						if(blogService.approveBlog(blogObj)){
							    blogObj = blogService.getBlog(blogId);
							    log.info("Approved Blogs: Approve Blog details successful "+blogObj);
							    //Add entry in Notification for the blog 
							    Notification notification = new Notification();
							    notification.setNotificationType("BLOG");
								notification.setNotificationReferenceId(blogObj.getBlogId());
								notification.setNotificationDesc(blogObj.getBlogTitle());
								notification.setUserId(blogObj.getUserId());
								notification.setApprovalStatus("APPROVED");
								notification.setViewed(false);
								notificationService.addNotification(notification);
								 log.info("Approved Blogs:Add notification details "+notification);
								return new ResponseEntity<Blog>(blogObj, HttpStatus.OK);
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
	}
	
	@RequestMapping(value="/rejectBlog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> rejectBlog(@PathVariable("blogId") int blogId,@RequestParam (required=false) String rejectionReason,HttpSession session){
		log.info("Reject Blogs: Reject Blog details  ");		
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
					Blog blog = blogService.getBlog(blogId);
					
					if(blog!=null){
						
						if(blogService.rejectBlog(blog)){
								blog = blogService.getBlog(blogId);
								
								Notification notification = new Notification();
								notification.setNotificationType("BLOG");
								notification.setNotificationReferenceId(blog.getBlogId());
								notification.setNotificationDesc(blog.getBlogTitle());
								notification.setUserId(blog.getUserId());
								notification.setApprovalStatus("REJECTED");
								notification.setViewed(false);
								if(rejectionReason==null){
									notification.setRejectionReason("Reason not mentioned by Admin");
								}
								else
								{
									notification.setRejectionReason(rejectionReason);
								}
								
								notificationService.addNotification(notification);
								
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
	
	
	@RequestMapping(value="/getBlogComment/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogComment(@PathVariable("blogId") int blogId){
		log.info("GetBlogComment: get blog comment by Id =  "+blogId);	
		List<BlogComment> commentList = blogCommentService.getAllBlogComments(blogId);
		
		if(commentList!=null){
			
				return new ResponseEntity<List<BlogComment>>(commentList, HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(17,"Retrieving Blog Comment details failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/addBlogComment",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		log.info("AddBlogComment: Blog comment Blog Id= "+blogComment.getBlogId()+" user Id = "+blogComment.getUserId() );
		Integer userId =  (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else{
				UsersDetails user = userService.getUserById(userId);
				if(user.isOnline()){	
					blogComment.setCommentDate(new Date());
					blogComment.setUserName(user.getFirstName()+" "+user.getLastName());
					if(blogCommentService.addBlogComment(blogComment)){
						return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(19,"Blog Comment Addition failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to add a comment"),HttpStatus.CONFLICT);
		    }
	}
		
	@RequestMapping(value="/updateLikes",method=RequestMethod.POST)
	public ResponseEntity<?> updateNoOfLikes(@RequestBody Blog blog,HttpSession session){
		log.info("Update No Of Likes ");
		Integer userId =  (Integer) session.getAttribute("userId");
		
		UsersDetails user = userService.getUserById(userId);
		if(user.isOnline()){			
			//if(blogService.updateBlog(blog)){
			Blog updatedBlog = blogPostLikesService.updateBlogPostLikes(blog, user);
			
			if(updatedBlog != null)
			{
				return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			}else{
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(11,"Blog Likes updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to like"),HttpStatus.CONFLICT);
	}
	
	
	@RequestMapping(value="/updateBlog",method=RequestMethod.PUT)
	public ResponseEntity<?> updateBlog(@RequestBody Blog blog,HttpSession session){
		log.info("Update Blogs: Update Blog details ");
		Integer userId = (Integer) session.getAttribute("userId");
		Notification notification;
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
				UsersDetails user = userService.getUserById(userId);
				
				if(user.isOnline()){
					Blog tempBlog = blogService.getBlog(blog.getBlogId());
					tempBlog.setBlogTitle(blog.getBlogTitle());
					tempBlog.setBlogContent(blog.getBlogContent());
					//if(tempBlog.getStatus().equals("REJECTED")){
						tempBlog.setStatus("PENDING");
						notification = notificationService.getNotification("BLOG", blog.getBlogId());
						
						if(notification != null){
							System.out.println("notification = "+notification.toString());
							if(notificationService.deleteNotification(notification)){
								System.out.println("Notification deleted successfully");
							}
						}
					//}
					if(blogService.updateBlog(tempBlog)){
						
						return new ResponseEntity<Blog>(tempBlog, HttpStatus.OK);
						
					}else{
						
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(21,"Blog Updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(22,"User must be logged in to update blog"),HttpStatus.CONFLICT);
			}
	}
	
	@RequestMapping(value="/deleteBlogById/{blogId}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteBlogById(@PathVariable("blogId") int blogId,HttpSession session){
		log.info("Delete Blogs: Delete Blog By Id "+ blogId);
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	UsersDetails user = userService.getUserById(userId);
		    	if(user.isOnline()){
					Blog tempBlog = blogService.getBlog(blogId);
					if(blogService.deleteBlog(tempBlog)){
						return new ResponseEntity<Blog>(tempBlog, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(23,"Blog Deletion failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(24,"User must be logged in to delete blog"),HttpStatus.CONFLICT);
		    	
		    }
	}
	
	
	@RequestMapping(value="/updateBlogComment",method=RequestMethod.POST)
	public ResponseEntity<?> updateBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		log.info("UpdateBlogComment: Blog comment Blog Id= "+blogComment.getBlogId()+" user Id = "+blogComment.getUserId() );
		Integer userId =  (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else{
				UsersDetails user = userService.getUserById(userId);
				if(user.isOnline()){	
					blogComment.setCommentDate(new Date());
					blogComment.setUserName(user.getFirstName()+" "+user.getLastName());
					if(blogCommentService.updateBlogComment(blogComment)){
						return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(19,"Blog Comment Updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to add a comment"),HttpStatus.CONFLICT);
		    }
	}
	
	
	@RequestMapping(value="/deleteBlogComment",method=RequestMethod.POST)
	public ResponseEntity<?> deleteBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		log.info("Delete BlogComment: Blog comment Blog Id= "+blogComment.getBlogId()+" user Id = "+blogComment.getUserId() );
		Integer userId =  (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else{
				UsersDetails user = userService.getUserById(userId);
				if(user.isOnline()){	
					
					if(blogCommentService.deleteBlogComment(blogComment)){
						return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
					}else{
						return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(19,"Blog Comment Deletion failed"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(12,"User must be logged in to add a comment"),HttpStatus.CONFLICT);
		    }
	}
}
