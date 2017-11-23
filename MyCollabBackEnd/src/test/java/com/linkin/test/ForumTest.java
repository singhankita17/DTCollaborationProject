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

import com.linkin.model.Forum;
import com.linkin.service.ForumService;

@Ignore
public class ForumTest {
	
	@Autowired
	private static	ForumService forumService;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void initialize(){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		
		context.scan("com.linkin");
		context.refresh();
		
		forumService = (ForumService) context.getBean("forumService");
	}

	
	@Test
	public void createForumTest(){
		
		Forum forum = new Forum();
		forum.setForumName("Angular JS");
		forum.setForumContent("Differnce between fatory, service and provider in Angular Js");
		forum.setUserId(46);
		forum.setCreatedDate(new Date());
		forum.setStatus("PENDING");
		assertTrue("Problem in adding new Forum ", forumService.addForum(forum));
	}
	

	@Test
	public void updateForumTest(){
		
		Forum forum = new Forum();
		
		forum.setForumId(52);
		forum.setForumName("Java Inheritance");
		forum.setForumContent("Does Java supports Multiple Inheritance ? ");
		forum.setStatus("PENDING");
		forum.setUserId(42);
		forum.setCreatedDate(new Date());
		
		assertTrue("Problem in updating Blog details",forumService.updateForum(forum));
		
	}
	
	@Ignore
	@Test
	public void deleteForumTest(){
		
		Forum forum = new Forum();
		
		forum.setForumId(53);
		
		
		assertTrue("Problem in updating Blog details",forumService.deleteForum(forum));
		
	}
	
	@Test
	public void getForumTest(){
		
		Forum forum = forumService.getForum(52);
		assertNotNull("Problem in retrieving Forum details",forum);
		System.out.println("Forum Id : "+forum.getForumId()+"  Forum Name : "+forum.getForumName()+"  Forum Content: "+forum.getForumContent()+" User Id: "+forum.getUserId()+"  Creation date : "+forum.getCreatedDate());
		
	}

	@Test
	public void getAllForumsOfUserTest(){
		
		List<Forum> forumList = forumService.getAllForumsOfUser(42);
		assertNotNull("Problem in retrieving Forum details of User",forumList);
		showForumDetails(forumList);
		
	}
	
	@Test
	public void getAllUsersForumTest(){
		
		List<Forum> forumList = forumService.getAllUsersForum();
		assertNotNull("Problem in retrieving All Forum details",forumList);
		showForumDetails(forumList);
		
	}
	
	private void showForumDetails(List<Forum> forumList) {
		
		for (Forum forum : forumList) {
			
			System.out.println(" Forum Id : "+forum.getForumId());
			System.out.println(" Forum Name : "+forum.getForumName());
			System.out.println(" Forum Content : "+forum.getForumContent());
			System.out.println(" Forum User Id : "+forum.getUserId());
			System.out.println(" Forum Creation Date : "+forum.getCreatedDate());
			System.out.println(" Forum Status : "+forum.getStatus());
		}
		
	}
}
