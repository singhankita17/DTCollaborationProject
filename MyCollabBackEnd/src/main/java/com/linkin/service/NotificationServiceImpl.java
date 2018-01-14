package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.NotificationDao;
import com.linkin.model.Notification;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationDao notificationDao;
	
	public List<Notification> getNotification(int userId, int viewed) {
		
		return notificationDao.getNotification(userId, viewed);
	}

	public Notification updateNotification(int notificationId) {
		
		return notificationDao.updateNotification(notificationId);
	}

	public boolean addNotification(Notification notification) {
		
		return notificationDao.addNotification(notification);
	}

}
