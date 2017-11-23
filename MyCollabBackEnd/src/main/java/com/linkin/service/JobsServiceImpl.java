package com.linkin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.JobsDao;
import com.linkin.model.JobDetail;

@Service("jobsService")
public class JobsServiceImpl implements JobsService {

	@Autowired
	JobsDao jobsDao;
	
	public boolean addJobs(JobDetail job) {
		
		return jobsDao.addJobs(job);
	}

	public boolean updateJobs(JobDetail job) {
		
		return jobsDao.updateJobs(job);
	}

	public boolean deleteJobs(JobDetail job) {
	
		return jobsDao.deleteJobs(job);
	}

	public JobDetail getJobs(int jobId) {
		
		return jobsDao.getJobDetail(jobId);
	}

	public List<JobDetail> getAllJobs() {
		
		return jobsDao.getAllJobDetails();
	}

}
