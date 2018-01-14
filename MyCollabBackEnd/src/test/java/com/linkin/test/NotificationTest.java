package com.linkin.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.linkin.model.Notification;
import com.linkin.service.NotificationService;

@Ignore
public class NotificationTest {

	@Autowired
	private static NotificationService notificationService;
	
	@BeforeClass
	public static void initialize(){
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.linkin");
		context.refresh();
		
		notificationService = (NotificationService) context.getBean("notificationService");
	}
	
	@Ignore
	@Test
	public void createNotificationTest(){
		
		Notification notification = new Notification();
		notification.setNotificationType("BLOG");
		notification.setNotificationReferenceId(202);
		notification.setApprovalStatus("REJECTED");
		notification.setRejectionReason(null);
		notification.setUserId(42);
		notification.setViewed(false);
		assertTrue("Problem in inserting Notification details",notificationService.addNotification(notification));
	}
	
	@Ignore
	@Test
	public void updateNotificationTest(){		
    
		assertNotNull("Problem in updating Notification details",notificationService.updateNotification(224));
	}
	
   
    @Test
	public void getNotificationTest(){
		
    	List<Notification> notificationList = notificationService.getNotification(42, 0);
		assertNotNull("Problem in retrieving Notification details",notificationList);
		for(Notification notification:notificationList){
			System.out.println("Notification Id: "+notification.getId());
			System.out.println("Notification Type : "+notification.getNotificationType());
			System.out.println("Notification Refernce Id : "+notification.getNotificationReferenceId());
			System.out.println("Notification userId: "+notification.getUserId());
			System.out.println("Notification approval status : "+notification.getApprovalStatus());
			System.out.println("Notification rejection reason : "+notification.getRejectionReason());
			System.out.println("Notification Viewed status: "+notification.isViewed());
			
		}
		
	}
  
}
