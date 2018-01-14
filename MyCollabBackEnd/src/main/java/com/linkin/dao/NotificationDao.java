package com.linkin.dao;

import java.util.List;

import com.linkin.model.Notification;

public interface NotificationDao {
	
	public boolean addNotification(Notification notification);

	public List<Notification> getNotification(int userId,int viewed);
	
	public Notification updateNotification(int notificationId);
	
}
