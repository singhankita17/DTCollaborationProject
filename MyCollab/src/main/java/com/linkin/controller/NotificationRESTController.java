package com.linkin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.model.Notification;
import com.linkin.service.NotificationService;
import com.linkin.utility.CollabApplicationError;

@Controller
public class NotificationRESTController {

	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value="/getnotification/{viewed}")
	public ResponseEntity<?> getNotification(@PathVariable("viewed")int viewed,HttpSession session){

		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId == null){
			  
			    return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
		else {
			List<Notification> notificationList = notificationService.getNotification(userId, viewed);
			
			if(notificationList == null){
			
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(51,"Notification details not found"),HttpStatus.INTERNAL_SERVER_ERROR);
			}else{
				
				return new ResponseEntity<List<Notification>>(notificationList,HttpStatus.OK);
			}
		}
	}
	
	
	@RequestMapping(value="/updatenotification/{notificationId}")
	public ResponseEntity<?> updateNotification(@PathVariable("notificationId")int notificationId,HttpSession session){

		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId == null){
			  
			    return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}	   
		else {
			Notification notification = notificationService.updateNotification(notificationId);
			
			if(notification == null){
			
				return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(51,"Notification details not found"),HttpStatus.INTERNAL_SERVER_ERROR);
			}else{
				
				return new ResponseEntity<Notification>(notification,HttpStatus.OK);
			}
		}
	}
	
	
}