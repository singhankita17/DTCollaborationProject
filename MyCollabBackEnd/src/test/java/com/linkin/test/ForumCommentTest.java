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

import com.linkin.model.ForumComment;
import com.linkin.service.ForumCommentService;

@Ignore
public class ForumCommentTest {

	@Autowired
	private static	ForumCommentService forumCommentService;
	
	@BeforeClass
	public static void initialize(){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.linkin");
		context.refresh();
		
		forumCommentService = (ForumCommentService) context.getBean("forumCommentService");
		
	}
	
    @Ignore
	@Test
	public void createForumCommentTest(){
		
		ForumComment comment = new ForumComment();
		
		comment.setForumId(52);
		comment.setCommentText("Java does not allow Multiple Inheritance");
		comment.setCommentDate(new Date());
		comment.setUserId(46);
		comment.setUserName("Geeta");
		
		assertTrue("Problem in adding new Forum Comment", forumCommentService.addForumComment(comment));
		
	}
	
    @Ignore
	@Test
	public void updateForumCommentTest(){
		
		ForumComment comment = new ForumComment();
		comment.setId(55);
		comment.setForumId(52);
		comment.setCommentText("Java does not Support Multiple Inheritance directly. A workaround can be achieved through Interfaces");
		comment.setCommentDate(new Date());
		comment.setUserId(22);
		comment.setUserName("Akshita Singh");
		
		assertTrue("Problem in adding new Forum Comment", forumCommentService.updateForumComment(comment));
		
	}
	
	
	@Test
	public void deleteForumCommentTest(){
		
		ForumComment comment = new ForumComment();
		comment.setId(56);
		comment.setForumId(52);
		comment.setCommentText("Java does not Support Multiple Inheritance directly.");
		comment.setCommentDate(new Date());
		comment.setUserId(46);
		
		
		assertTrue("Problem in adding new Forum Comment", forumCommentService.deleteForumComment(comment));
		
	}
	
	@Test
	public void getForumTest(){
		
		ForumComment comment = forumCommentService.getForumComment(55);
		assertNotNull("Problem in retrieving Forum details",comment);
		System.out.println("Forum Comment Id : "+comment.getId()+"  Comment Text : "+comment.getCommentText()+"  Comment User Id : "+comment.getUserId()+"  Creation date : "+comment.getCommentDate());
		
	}
	
	@Test
	public void getAllForumComments(){
		List<ForumComment> forumCommentList = forumCommentService.getAllForumComments(52);
		assertNotNull("Problem in retrieving All Forum details",forumCommentList);
		showForumCommentDetails(forumCommentList);
		
	}


	private void showForumCommentDetails(List<ForumComment> forumCommentList) {
		
		for (ForumComment forumComment : forumCommentList) {
			
			System.out.println(" Comment Id: "+forumComment.getId());
			System.out.println(" Forum Id: "+forumComment.getForumId());
			System.out.println(" Comment Text: "+forumComment.getCommentText());
			System.out.println(" Comment Date: "+forumComment.getCommentDate());
			System.out.println(" User Id : "+forumComment.getUserId());
		}
		
	}
	
}
