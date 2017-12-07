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

import com.linkin.model.Blog;
import com.linkin.service.BlogService;


public class BlogTest {

	@Autowired
	private static BlogService blogService;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void initialize(){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.linkin");
		context.refresh();
		
		blogService = (BlogService) context.getBean("blogService");
		
	}
	
	@Ignore
	@Test
	public void createBlogTest(){
		
		Blog blog = new Blog();
		
		blog.setBlogTitle("Angular JS");
		blog.setBlogContent("Angular Js Tutorial for Client side scripting.");
		blog.setStatus("PENDING");
		blog.setCreatedDate(new Date());
		blog.setUserId(46);
		
		assertTrue("Problem in storing Blog details",blogService.addBlog(blog));
		
	}
	
	@Ignore
	@Test
	public void updateBlogTest(){
		
		Blog blog = new Blog();
		blog.setBlogId(47);
		blog.setBlogTitle("Spring MVC Framework");
		blog.setBlogContent("Spring Web MVC is the original web framework built on the Servlet API");
		blog.setStatus("PENDING");
		blog.setCreatedDate(new Date());
		blog.setUserId(42);
		
		assertTrue("Problem in updating Blog details",blogService.updateBlog(blog));
		
	}
	
	@Ignore
	@Test
	public void deleteBlogTest(){
		
		Blog blog = new Blog();
		blog.setBlogId(47);
		blog.setBlogTitle("Spring MVC Framework");
		blog.setBlogContent("Spring Web MVC is the original web framework built on the Servlet API");
		blog.setStatus("PENDING");
		blog.setCreatedDate(new Date());
		blog.setUserId(42);
		
		assertTrue("Problem in deleting Blog details",blogService.deleteBlog(blog));
		
	}
	
	@Ignore
	@Test
	public void approveBlogTest(){
		
		Blog blog = new Blog();
		blog.setBlogId(47);
		blog.setBlogTitle("Spring MVC");
		blog.setBlogContent("Spring Web MVC is the original web framework built on the Servlet API and included in the Spring Framework from the very beginning.");
		blog.setStatus("APPROVED");
		blog.setCreatedDate(new Date());
		blog.setUserId(42);
		
		assertTrue("Problem in approving Blog details",blogService.approveBlog(blog));
		
	}
	
	@Test
	public void getBlogTest(){
		
		Blog blog = blogService.getBlog(47);
		assertNotNull("Problem in retrieving Blog details",blog);
		System.out.println("Blog Id : "+blog.getBlogId()+"  Blog title : "+blog.getBlogTitle()+"  Blog Content: "+blog.getBlogContent()+"\n User Id: "+blog.getUserId());
		
	}
	
	
	@Test
	public void getAllBlogsTest(){
		
		List<Blog> blogList = blogService.getAllBlogs(42);
		assertNotNull("Problem in retrieving Blog details",blogList);
		showBlogDetails(blogList);
		
	}
	
	
	@Test
	public void getAllUsersBlogTest(){
		
		List<Blog> blogList = blogService.getAllBlogs();
		assertNotNull("Problem in retrieving Blog details",blogList);
		showBlogDetails(blogList);
		
	}

	private void showBlogDetails(List<Blog> blogList) {
		
		for (Blog blog : blogList) {
			System.out.println("Blog Id : "+blog.getBlogId());
			System.out.println("Blog Title : "+blog.getBlogTitle());
			System.out.println("Blog Content : "+blog.getBlogContent());
			System.out.println("Blog Creation Date : "+blog.getCreatedDate());
			System.out.println("Blog Status : "+blog.getStatus());
			System.out.println("Blog UserId : "+blog.getUserId());
		}
	}
	
}
