package com.linkin.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.linkin.model.JobDetail;
import com.linkin.service.JobsService;

@Ignore
public class JobsTest {
	
	@Autowired
	private static JobsService jobsService;
	
	@BeforeClass
	public static void initialize(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.linkin");
		context.refresh();
		
		jobsService = (JobsService) context.getBean("jobsService");
	}

	@Ignore
	@Test
	public void createJobsTest(){
		
		JobDetail job = new JobDetail();
		job.setJobDesc("Responsible for coding, testing and deploying user friendly applications");
		job.setJobProfile("Software Engineer");
		job.setQualification("B.Tech");
		job.setPostDate(new Date());
		
		assertTrue("Problem in storing Job details",jobsService.addJobs(job));
	}
	

	@Test
	public void updateJobsTest(){
		
		JobDetail job = new JobDetail();
		job.setId(60);
		job.setJobDesc("Responsible for coding, testing and deploying user friendly applications");
		job.setJobProfile("Software Engineer");
		job.setQualification("B.Tech");
		job.setPostDate(new Date());
		
		assertTrue("Problem in updating job details",jobsService.updateJobs(job));
	}
	
	
	@Test
	public void deleteJobsTest(){
		
		JobDetail job = new JobDetail();
		job.setId(63);
		job.setJobDesc("Responsible for coding, testing and deploying user friendly applications");
		job.setJobProfile("Software Engineer");
		job.setQualification("B.Tech");
		job.setPostDate(new Date());
		
		assertTrue("Problem in deleting job details",jobsService.deleteJobs(job));
	}
	
	@Test
	public void getJobTest(){
		
		JobDetail job = jobsService.getJobs(60);
		assertNotNull("Problem in retrieving Job details",job);
		System.out.println("Job Id : "+job.getId()+"  Job Desc: "+job.getJobDesc()+"  Job Profile : "+job.getJobProfile()+"  Job Qualification : "+job.getQualification()+"  Job "+job.getPostDate());
		
	}
	

	@Test
	public void getAllJobsTest(){
		
		List<JobDetail> jobsList = jobsService.getAllJobs();
		assertNotNull("Problem in retrieving All Jobs details",jobsList);
		showJobsDetails(jobsList);
		
	}

	private void showJobsDetails(List<JobDetail> jobsList) {
		
		for (JobDetail jobs : jobsList) {
			
			System.out.println(" Job Id : "+jobs.getId());
			System.out.println(" Job Desc : "+jobs.getJobDesc());
			System.out.println(" Job Profile : "+jobs.getJobProfile());
			System.out.println(" Job Qualification : "+jobs.getQualification());
			System.out.println(" Job Posted Date : "+jobs.getPostDate());
			
		}
		
	}
}
