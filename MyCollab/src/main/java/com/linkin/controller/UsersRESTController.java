package com.linkin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
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
	public ResponseEntity<?> retrieveUserById(@PathVariable("id") int id){
		System.out.println("User Id: "+id);
		
		UsersDetails userObj = usersService.getUserById(id);
		
		if(userObj!=null){
			System.out.println("User Object: "+userObj.toString());
			return new ResponseEntity<UsersDetails>(userObj,HttpStatus.OK);	
		
		}else{
			
			System.out.println("User Object:IS NULL");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(5,"User Details Not Found"), HttpStatus.NOT_FOUND);	
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
		
		boolean ifExists = usersService.checkIfValidUser(users.getUserName(), users.getPassword());
		
		System.out.println("User Exists = "+ifExists);
			    
		if(ifExists){
			users = usersService.getUserByName(users.getUserName());
			users.setOnline(true);
	        usersService.updateUser(users);
			session.setAttribute("user", users);
			return new ResponseEntity<UsersDetails>(users, HttpStatus.OK);
			
		}else{
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(6,"User or Password does not match"), HttpStatus.CONFLICT);
		}	
	
	}
	
	/************************Logout ***********************************/
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{ 
	  
	    UsersDetails validUser=(UsersDetails) session.getAttribute("user");
	    System.out.println("user in session = "+validUser);
	    
	    if(validUser==null)
	    {
	    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(5,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
	    else	
	    {
	        validUser.setOnline(false);
	        usersService.updateUser(validUser);
		    session.removeAttribute("user");
		    session.invalidate();
		    return new ResponseEntity<UsersDetails>(validUser,HttpStatus.OK);    
		}
	}
	
}
