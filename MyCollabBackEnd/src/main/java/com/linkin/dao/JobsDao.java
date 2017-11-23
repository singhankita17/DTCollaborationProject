package com.linkin.dao;

import java.util.List;

import com.linkin.model.JobDetail;

public interface JobsDao {
	
	public boolean addJobs(JobDetail job);
	
	public boolean updateJobs(JobDetail job);
	
	public boolean deleteJobs(JobDetail job);
	
	public JobDetail getJobDetail(int jobId);
	
	public List<JobDetail> getAllJobDetails();
	
}
