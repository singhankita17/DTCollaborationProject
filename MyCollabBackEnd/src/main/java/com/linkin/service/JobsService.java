package com.linkin.service;

import java.util.List;

import com.linkin.model.JobDetail;

public interface JobsService {

	public boolean addJobs(JobDetail job);
	
	public boolean updateJobs(JobDetail job);
	
	public boolean deleteJobs(JobDetail job);
	
	public JobDetail getJobs(int jobId);
	
	public List<JobDetail> getAllJobs();
	
}
