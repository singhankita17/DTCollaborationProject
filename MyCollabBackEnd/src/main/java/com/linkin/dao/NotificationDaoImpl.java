package com.linkin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.Notification;

@Repository
public class NotificationDaoImpl implements NotificationDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public List<Notification> getNotification(int userId, int viewed) {
		
		boolean viewStatus;
		
		if(viewed==0){
			viewStatus = false;
		}else{
			viewStatus = true;
		}
		Session session = sessionFactory.getCurrentSession();
		List<Notification> notificationList= session.createQuery(" from Notification where userId = ? and viewed = ?",Notification.class)
				 .setParameter(0, userId).setParameter(1, viewStatus).list();
		
		return notificationList;
	}

	@Transactional
	public Notification updateNotification(int notificationId) {
		Session session = sessionFactory.getCurrentSession();
		Notification notification= (Notification) session.get(Notification.class, notificationId);
		notification.setViewed(true);
		session.update(notification);
		return notification;
	}

	@Transactional
	public boolean addNotification(Notification notification) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.save(notification);
			return true;
		}catch(Exception e){
		return false;
		
		}
	}

}
