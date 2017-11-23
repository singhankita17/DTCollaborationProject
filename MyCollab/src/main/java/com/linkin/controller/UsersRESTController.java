package com.linkin.controller;

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

@RestController
public class UsersRESTController {

	
	@Autowired
	private UsersService usersService;

	
	@RequestMapping(value="/retrieveUser/{username}",method=RequestMethod.GET,produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDetails> retrieveUser(@PathVariable("username") String username){
		System.out.println("EMAIL: "+username);
		UsersDetails userObj = usersService.getUserByName(username);
		
		if(userObj!=null){
			System.out.println("User Object: "+userObj.toString());
			return new ResponseEntity<UsersDetails>(userObj,HttpStatus.OK);	
		
		}else{
			
			System.out.println("User Object:IS NULL");
			return new ResponseEntity<UsersDetails>(new UsersDetails(),HttpStatus.CONFLICT);	
		}
	
	}
	
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public ResponseEntity<UsersDetails> registerNewUser(@RequestBody UsersDetails user){
		boolean ifExists = usersService.checkIfExistingUser(user);
		System.out.println("User Exists = "+ifExists);
		if(ifExists){
			
			return new ResponseEntity<UsersDetails>(user,HttpStatus.CONFLICT);
		}else{
			boolean isUserSaved  = usersService.saveOrUpdate(user);
			if(isUserSaved)
				return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
			else
				return new ResponseEntity<UsersDetails>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<UsersDetails> loginUser(@RequestBody UsersDetails user){
		boolean ifExists = usersService.checkIfValidUser(user);
		System.out.println("User Exists = "+ifExists);
		if(ifExists){
			
			return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
		}else{
			return new ResponseEntity<UsersDetails>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	
	}
	
	
	
	
}
