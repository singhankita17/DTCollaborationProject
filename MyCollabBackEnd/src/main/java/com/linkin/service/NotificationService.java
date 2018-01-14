package com.linkin.service;

import java.util.List;

import com.linkin.model.Notification;

public interface NotificationService {
	
	public boolean addNotification(Notification notification);

	public List<Notification> getNotification(int userId,int viewed);
	
	public Notification updateNotification(int notificationId);
}
