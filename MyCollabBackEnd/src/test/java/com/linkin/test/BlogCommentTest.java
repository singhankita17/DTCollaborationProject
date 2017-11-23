package com.linkin.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.linkin.model.BlogComment;
import com.linkin.service.BlogCommentService;

@Ignore
public class BlogCommentTest {
	
		@Autowired
		private static BlogCommentService blogCommentService;

		@SuppressWarnings("resource")
		@BeforeClass
		public static void initialize(){
			
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.scan("com.linkin");
			context.refresh();
			
			blogCommentService = (BlogCommentService) context.getBean("blogCommentService");
			
		}
		
		
		@Test
		public void createBlogCommentTest(){
			
			BlogComment comment = new BlogComment();
			
			comment.setBlogId(47);
			comment.setCommentText("Well written!!!");
			comment.setCommentDate(new Date());
			comment.setUserId(46);
			assertTrue("Problem in storing Blog details",blogCommentService.addBlogComment(comment));
			
		}
		
		@Test
		public void updateBlogTest(){
			
			BlogComment comment = new BlogComment();
			comment.setId(50);
			comment.setBlogId(47);
			comment.setCommentText("Can be more informative");
			comment.setUserId(42);
			assertTrue("Problem in updating Blog details",blogCommentService.updateBlogComment(comment));
			
		}
		
		@Test
		public void getBlogCommentTest(){
			
			BlogComment comment = blogCommentService.getBlogComment(50);
			assertNotNull("Problem in retrieving Blog details",comment);
			System.out.println("Blog Id : "+comment.getBlogId()+"  Comment Text : "+comment.getCommentText()+"  Comment date: "+comment.getCommentDate()+" User Id: "+comment.getUserId());
			
		}
		
		
		@Test
		public void getAllBlogsTest(){
			
			List<BlogComment> blogCommentList = blogCommentService.getAllBlogComments(47);
			assertNotNull("Problem in retrieving Blog details",blogCommentList);
			showBlogDetails(blogCommentList);
			
		}

		private void showBlogDetails(List<BlogComment> blogCommentList) {
			
			for (BlogComment blogComment : blogCommentList) {
				System.out.println("Comment Id : "+blogComment.getId());
				System.out.println("Blog Id : "+blogComment.getBlogId());
				System.out.println("Comment Text : "+blogComment.getCommentText());
				System.out.println("Comment Creation Date : "+blogComment.getCommentDate());
				System.out.println("Comment UserId : "+blogComment.getUserId());
			}
		}
		
}