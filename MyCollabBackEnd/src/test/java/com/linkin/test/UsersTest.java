package com.linkin.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.linkin.model.UsersDetails;
import com.linkin.service.UsersService;

@Ignore
public class UsersTest {

	
	@Autowired
	private static UsersService usersService;
	
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void initialize(){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.linkin.*");
		context.refresh();
		
		usersService = (UsersService) context.getBean("usersService");
		
	}
	
	@Ignore
	@Test
	public void createUserTest(){
		
		UsersDetails user = new UsersDetails();
		
		user.setFirstName("Geeta");
		user.setLastName("Arora");
		user.setUserName("Geeta12");
		user.setEmail("geeta@gmail.com");
		user.setPassword("123456");
		user.setContact("9826245622");
		user.setRole("STUDENT");
		user.setEnabled(true);
		user.setOnline(false);
		
		assertTrue("Problem in storing user details",usersService.saveUser(user));
		
	}
	
	@Test
	public void retrieveUserTest(){
		
		UsersDetails user =usersService.getUserByEmail("geeta@gmail.com");
		assertNotNull("Problem in retrieving user details",user);
		System.out.println(" First Name : "+user.getFirstName()+" Last Name :  "+user.getLastName()+"\n Email : "+user.getEmail()+"  User Role : "+user.getRole());
		
	}
	
	@Test
	public void updateUserTest(){
		
		UsersDetails user = new UsersDetails();
		user.setC_user_id(46);
		user.setFirstName("Geeta");
		user.setLastName("Arora");
		user.setUserName("Geeta12");
		user.setEmail("geeta@gmail.com");
		user.setPassword("112233");
		user.setContact("9826245622");
		user.setRole("STUDENT");
		user.setEnabled(true);
		user.setOnline(false);
		
		assertTrue("Problem in storing user details",usersService.updateUser(user));
		
		
	}
	
	
	@Test
	public void deleteUserTest(){
		
		
		assertTrue("Problem in storing user details",usersService.deleteUser(2));
	}
	
	@Test
	public void retrieveAllUsersTest(){
		
		List<UsersDetails> userList =usersService.getAllUsers();
		assertNotNull("Problem in retrieving user details",userList);
		showUserList(userList);
		
	}

	private void showUserList(List<UsersDetails> userList) {
	
		for (UsersDetails usersDetails : userList) {
			
			System.out.println("User Id: "+usersDetails.getC_user_id());
			System.out.println("User First Name: "+usersDetails.getFirstName());
			System.out.println("User LastName: "+usersDetails.getLastName());
			System.out.println("User Email Id: "+usersDetails.getEmail());
			System.out.println("User UserName: "+usersDetails.getUserName());
			System.out.println("User contact: "+usersDetails.getContact());
			System.out.println("User Role: "+usersDetails.getRole());
			System.out.println();
		}
		
	}
	
}
