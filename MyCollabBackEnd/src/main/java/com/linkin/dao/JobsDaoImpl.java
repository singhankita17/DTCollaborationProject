package com.linkin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.model.JobDetail;

@Repository
public class JobsDaoImpl implements JobsDao {

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public boolean addJobs(JobDetail job) {
		try
		{
		    sessionFactory.getCurrentSession().save(job);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean updateJobs(JobDetail job) {
		try{
			sessionFactory.getCurrentSession().update(job);
			return true;
		}catch(Exception e){
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public boolean deleteJobs(JobDetail job) {
		try
		{
			sessionFactory.getCurrentSession().delete(job);
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception raised: "+e);
			return false;
		}
	}

	@Transactional
	public JobDetail getJobDetail(int jobId) {
		
		Session session = sessionFactory.openSession();
		
		JobDetail jobsObj = session.get(JobDetail.class, jobId);
		
		session.close();
		
		return jobsObj;
	}

	@Transactional
	public List<JobDetail> getAllJobDetails() {

		Session session = sessionFactory.openSession();
		
		List<JobDetail> jobsList= session.createQuery("from JobDetail",JobDetail.class).list();
		
		session.close();
		
		return jobsList;
	}

}
