package com.linkin.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;
import com.linkin.service.FriendService;

@Ignore
public class FriendTest {
	
	@Autowired
	private static FriendService friendService;
	
	@BeforeClass
	public static void initialize(){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.linkin");
		context.refresh();
		
		friendService = (FriendService) context.getBean("friendService");
	}

	@Test
	public void testListOfSuggestedUsers() {
		
		UsersDetails user = new UsersDetails();
		user.setC_user_id(22);
		
		List<UsersDetails> suggestedList = friendService.listOfSuggestedUsers(user.getC_user_id());
		showUserList(suggestedList);
		assertNotNull("Problem in fetching list of suggested users", suggestedList);
		
	}

    @Ignore
	@Test
	public void testProcessFriendRequest() {
				
		assertTrue("Problem in processing friend request",friendService.processFriendRequest(22, 46));
		
	}
    
    public void showUserList(List<UsersDetails> suggestedList){
    	UsersDetails usersDetails =null;
    	Iterator<UsersDetails> iterator = suggestedList.iterator();
    	while(iterator.hasNext()) {
    		try{
    			
    		usersDetails =  (UsersDetails) iterator.next();
    		
    		}catch(Exception e){
    			
    			System.out.println("User error = "+ e.getMessage());
    		}
    		System.out.println(" Username: "+ usersDetails.getUserName());
    	}
    	
    }
    
    @Test
    public void listOfFriendsTest(){
    	assertNotNull("Problem in fetching list of friends",friendService.listOfFriends(42));
    	
    }
    
    @Test
    public void deleteFriendTest(){
    	Friend friend = new Friend();
    	friend.setId(191);
    	friend.setFromId(42);
    	friend.setToId(46);
    	friend.setStatus("ACCEPTED");
    	
    	assertNotNull("Problem in deleting list of friends",friendService.deleteFriend(friend, 42));
    	
    }

}
