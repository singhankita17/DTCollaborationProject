package com.linkin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linkin.model.Friend;
import com.linkin.model.UsersDetails;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class UsersRESTController {

	
	@Autowired
	private UsersService usersService;

	
	@RequestMapping(value="/retrieveUser/{username}",method=RequestMethod.GET,produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUser(@PathVariable("username") String username){
		System.out.println("EMAIL: "+username);
		UsersDetails userObj = usersService.getUserByName(username);
		
		if(userObj!=null){
			System.out.println("User Object: "+userObj.toString());
			return new ResponseEntity<UsersDetails>(userObj,HttpStatus.OK);	
		
		}else{
			
			System.out.println("User Object:IS NULL");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(1,"This User does not exist"),HttpStatus.CONFLICT);	
		}
	
	}
	
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public ResponseEntity<?> registerNewUser(@RequestBody UsersDetails user){
		boolean ifExists = usersService.checkIfExistingUser(user);
		System.out.println("User Exists = "+ifExists);
		if(ifExists){
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(2,"This User is already registered"),HttpStatus.CONFLICT);
		}
		else
		{
			boolean isUserSaved  = usersService.saveUser(user);
			
			if(isUserSaved)
				return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
			else
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(3,"User registration failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
	}
	
	
	@RequestMapping(value="/updateUser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateExistingUser(@RequestBody UsersDetails user){
		
		boolean isUpdated = usersService.updateUser(user);
		
		System.out.println("User Updated = "+isUpdated);
		
		if(isUpdated){
			
			return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
		
		}else{
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(4,"User Updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
	}
	
	@RequestMapping(value="/retrieveUserById/{id}",method=RequestMethod.GET,produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUserById(@PathVariable("id") int id,HttpSession session){
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	System.out.println("User Id: "+id);
		
		    	UsersDetails userObj = usersService.getUserById(userId);
		
				if(userObj!=null){
					System.out.println("User Object: "+userObj.toString());
					return new ResponseEntity<UsersDetails>(userObj,HttpStatus.OK);	
				
				}else{
					
					System.out.println("User Object:IS NULL");
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(5,"User Details Not Found"), HttpStatus.NOT_FOUND);	
				}
		    }
	
	}
	
	
	@RequestMapping(value="/retrieveAllUsers",method=RequestMethod.GET)
	public ResponseEntity<?> retrieveAllUsers(){
				
		List<UsersDetails> usersList = usersService.getAllUsers();
		
		if(usersList!=null){
			System.out.println("User Object: "+usersList);
			return new ResponseEntity<List<UsersDetails>>(usersList,HttpStatus.OK);	
		
		}else{
			
			System.out.println("User Object:IS NULL");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(5,"User Details Not Found"), HttpStatus.NOT_FOUND);
		}
	
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody UsersDetails users,HttpSession session){
		
		System.out.println("User Name = "+users.getUserName());
		System.out.println("Password = "+users.getPassword());
		UsersDetails validUser;
		boolean ifExists = usersService.checkIfValidUser(users.getUserName(), users.getPassword());
		
		System.out.println("User Exists = "+ifExists);
			    
		if(ifExists){
			
			validUser = usersService.getUserByName(users.getUserName());
			if(usersService.checkIfAlreadyLoggedInUser(validUser)){
				
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(8,"This user already logged in. Please logout first to login again."), HttpStatus.CONFLICT);
			}
			validUser.setOnline(true);
	        usersService.updateUser(validUser);
			session.setAttribute("userId", validUser.getC_user_id());
			return new ResponseEntity<UsersDetails>(validUser, HttpStatus.OK);
			
		}else{
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(6,"UserName or Password is incorrect"), HttpStatus.CONFLICT);
		}	
	
	}
	
	/************************Logout ***********************************/
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{ 
	    Integer userId = (Integer) session.getAttribute("userId");
	   
	    
	    if(userId==null)
	    {
	    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
	    else	
	    {
	    	UsersDetails validUser= usersService.getUserById(userId);
	 	    System.out.println("user in session = "+validUser);
	        validUser.setOnline(false);
	        usersService.updateUser(validUser);
		    session.removeAttribute("userId");
		    session.invalidate();
		    return new ResponseEntity<UsersDetails>(validUser,HttpStatus.OK);    
		}
	}
	
	
	
	@RequestMapping(value="/uploadUserImage",method=RequestMethod.POST)
	public ResponseEntity<?> uploadUserImage(@RequestParam CommonsMultipartFile image,HttpSession session){
		 Integer userId = (Integer) session.getAttribute("userId");
		   		
		    if(userId==null)
		    {
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else
		    {
		    	UsersDetails user = usersService.getUserById(userId);
		    	
				user.setImage(image.getBytes());
					
		        if(usersService.updateUser(user)){
		        
					return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
				
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(4,"User Image Updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
		    }
	
	}
	
	
	@RequestMapping(value="/getimage/{id}", method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePic(@PathVariable("id") int id,HttpSession session){
		/*Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		 {
		    	return null;
		 }	
		else
		{*/
			UsersDetails user = usersService.getUserById(id);
			byte[] profilePic = user.getImage();
			
			if(profilePic==null)
				return null;
			else			
				return profilePic;
	//	}
		
	}
	
	
	@RequestMapping(value="/getOnlineUserList",method=RequestMethod.GET)
	public ResponseEntity<?> getOnlineUserList(HttpSession session){
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId == null){
			  
			    return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
		else {
			
			List<Integer> userList = usersService.getOnlineUserList();
			
			if(userList!=null){
				
				return new ResponseEntity<List<Integer>>(userList,HttpStatus.OK);
				
			}else{
				
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(47,"Error in fetching online list of users"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@RequestMapping(value="/getAllUsersName",method=RequestMethod.GET)
	public ResponseEntity<?> getAllUsersName(){
		
		
			Map<Integer, String> userNames = usersService.getAllUsersFullNames();
							
			if(userNames!=null){
				
				return new ResponseEntity<Map<Integer,String>>(userNames,HttpStatus.OK);
				
			}else{
				
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(44,"Error in fetching Names of user"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
		
}
