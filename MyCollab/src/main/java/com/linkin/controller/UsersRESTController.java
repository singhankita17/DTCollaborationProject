package com.linkin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger log = LoggerFactory.getLogger(UsersRESTController.class);
	
	@RequestMapping(value="/retrieveUser/{username}",method=RequestMethod.GET,produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUser(@PathVariable("username") String username){
		
		log.info("Retrieve User by Username : EMAIL: "+username);
		UsersDetails userObj = usersService.getUserByName(username);
		
		if(userObj!=null){
			
			log.info("Retrieved User by Object : "+userObj.toString());
			return new ResponseEntity<UsersDetails>(userObj,HttpStatus.OK);	
		
		}else{
			log.info("Retrieved User Object IS NULL: ");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(1,"This User does not exist"),HttpStatus.CONFLICT);	
		}
			
	
	}
	
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public ResponseEntity<?> registerNewUser(@RequestBody UsersDetails user){
		boolean ifExists = usersService.checkIfExistingUser(user);
		
		log.info("Registration of New User : If user already exists status = "+ifExists) ;
		
		if(ifExists){
			
			log.info("Registration of New User: If user already exists ");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(2,"This User is already registered"),HttpStatus.CONFLICT);
		}
		else
		{
			boolean isUserSaved  = usersService.saveUser(user);
			
			
			if(isUserSaved){
				
				log.info("Registration of New User: User details saved successfully ");
				return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
			}
			else{
				log.info("Registration of New User: Failed ");
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(3,"User registration failed"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	}
	
	
	@RequestMapping(value="/updateUser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateExistingUser(@RequestBody UsersDetails user,HttpSession session){
		log.info("Update User Details :Inside Update User details ");
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
			 	log.info("Update User Details : user session details not found ");
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
				boolean isUpdated = usersService.updateUser(user);
				log.info("Update User Details : user update status = "+isUpdated);
				
				if(isUpdated){
					return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
				
				}else{
					
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(4,"User Updation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
		    }
			
	}
	
	
	@RequestMapping(value="/retrieveUserById/{id}",method=RequestMethod.GET,produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveUserById(@PathVariable("id") int id,HttpSession session){
		log.info("Retrieve User By ID : "+id);
		Integer userId = (Integer) session.getAttribute("userId");
		 if(userId==null)
		    {
			 	log.info("Retrieve User By ID  : user session details not found ");
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else	
		    {
		    	log.info("Retrieve User By ID : "+id);
		
		    	UsersDetails userObj = usersService.getUserById(userId);
		
				if(userObj!=null){
					log.info("Retrieve User By ID : User Object = "+userObj.toString());
					return new ResponseEntity<UsersDetails>(userObj,HttpStatus.OK);	
				
				}else{
					
					log.info("Retrieve User By ID : User Object : IS NULL");
					return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(5,"User Details Not Found"), HttpStatus.NOT_FOUND);	
				}
		    }
	
	}
	
	
	@RequestMapping(value="/retrieveAllUsers",method=RequestMethod.GET)
	public ResponseEntity<?> retrieveAllUsers(){
			
		log.info("Retrieve All Users  Details ");
		List<UsersDetails> usersList = usersService.getAllUsers();
		
		if(usersList!=null){
			log.info("Retrieve All Users  Details : User Object: "+usersList);
			return new ResponseEntity<List<UsersDetails>>(usersList,HttpStatus.OK);	
		
		}else{
			
			log.info("Retrieve All Users  Details : User Object:IS NULL");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(5,"User Details Not Found"), HttpStatus.NOT_FOUND);
		}
	
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> loginUser(@RequestBody UsersDetails users,HttpSession session){
		
		log.info("Login Method for User :") ;
		log.info("Login Method for User :User Name = "+users.getUserName());
		log.info("Login Method for User :Password = "+users.getPassword());
		
		UsersDetails validUser;
		boolean ifExists = usersService.checkIfValidUser(users.getUserName(), users.getPassword());
		
		log.info("Login Method for User : User Exists = "+ifExists);
			    
		if(ifExists){
			
			validUser = usersService.getUserByName(users.getUserName());
			log.info("Login Method for User : fetched user details --"+validUser.toString());
			if(usersService.checkIfAlreadyLoggedInUser(validUser)){
				
				log.info("Login Method for User : Already Logged in User --");
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(8,"This user already logged in. Please logout first to login again."), HttpStatus.CONFLICT);
			}
			log.info("Login Method for User : Set user online status true and set session attributes and return successful login");
			validUser.setOnline(true);
	        usersService.updateUser(validUser);
			session.setAttribute("userId", validUser.getC_user_id());
			return new ResponseEntity<UsersDetails>(validUser, HttpStatus.OK);
			
		}else{
			log.info("Login Method for User : Login Failed due to incorrect username or password");
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(6,"UserName or Password is incorrect"), HttpStatus.CONFLICT);
		}	
	
	}
	
	/************************Logout ***********************************/
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{ 
		log.info("LogOut Method for User : Inside Method");
	    Integer userId = (Integer) session.getAttribute("userId");
	   
	    log.info("LogOut Method for User : Check User session exists");
	    if(userId==null)
	    {
	    	log.info("LogOut Method for User :  User session details not found -- Unauthorized User");
	    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
	    else	
	    {
	    	UsersDetails validUser= usersService.getUserById(userId);
	    	log.info("LogOut Method for User :  User session details found  "+validUser.toString());
	        validUser.setOnline(false);
	        usersService.updateUser(validUser);
		    session.removeAttribute("userId");
		    session.invalidate();
		    log.info("LogOut Method for User :  Set user online status false and invalidate the session ");
		    return new ResponseEntity<UsersDetails>(validUser,HttpStatus.OK);    
		}
	}
	
	
	
	@RequestMapping(value="/uploadUserImage",method=RequestMethod.POST)
	public ResponseEntity<?> uploadUserImage(@RequestParam CommonsMultipartFile image,HttpSession session){
		log.info("UploadUserImage :  Check User session details");
		 Integer userId = (Integer) session.getAttribute("userId");
		   		
		    if(userId==null)
		    {
		    	log.info("UploadUserImage :  User session details not found");
		    	return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
			}	   
		    else
		    {
		    	log.info("UploadUserImage :  User session details found");
		    	UsersDetails user = usersService.getUserById(userId);
		    	log.info("UploadUserImage :  save image to user object");
				user.setImage(image.getBytes());
					
		        if(usersService.updateUser(user)){
		        	
		        	log.info("UploadUserImage : successfully update image to user object");
		        
					return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
				
				}else{
					
					log.info("UploadUserImage : Image upload failed");
					
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
		
		log.info("Get User ProfilePic : fetch Image");
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
		
		log.info("GetOnlineUserList: get User session details");
		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId == null){
			  
			    return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
		else {
			
			List<Integer> userList = usersService.getOnlineUserList();
			log.info("GetOnlineUserList: fetcht Online User details = "+userList);
			if(userList!=null){
				
				return new ResponseEntity<List<Integer>>(userList,HttpStatus.OK);
				
			}else{
				
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(47,"Error in fetching online list of users"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	@RequestMapping(value="/getAllUsersName",method=RequestMethod.GET)
	public ResponseEntity<?> getAllUsersName(){
		
		    log.info("GetAllUsersName: fetch All User full names");
			Map<Integer, String> userNames = usersService.getAllUsersFullNames();
			log.info("GetAllUsersName: All User full names fetched "+userNames);		
			if(userNames!=null){
				log.info("GetAllUsersName: All User full names fetched successfully");			
				return new ResponseEntity<Map<Integer,String>>(userNames,HttpStatus.OK);
				
			}else{
				
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(44,"Error in fetching Names of user"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
		
}
