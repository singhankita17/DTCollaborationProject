package com.linkin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.JobDetail;
import com.linkin.model.UsersDetails;
import com.linkin.service.JobsService;
import com.linkin.service.UsersService;
import com.linkin.utility.CollabApplicationError;

@RestController
public class JobRESTController {
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	JobsService jobsService;
	
	private static Logger log = LoggerFactory.getLogger(JobRESTController.class);

	@RequestMapping(value="/addJob",method=RequestMethod.POST)
	public ResponseEntity<?> addJob(@RequestBody JobDetail job,HttpSession session){
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId==null){
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}
		
		UsersDetails user = usersService.getUserById(userId);
		
		if(!user.getRole().equals("ADMIN")){
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(8,"Access Denied"),HttpStatus.UNAUTHORIZED);
		}
		
		job.setPostedOn(new Date());
		
		if(jobsService.addJobs(job)){
			
			return new ResponseEntity<JobDetail>(job,HttpStatus.OK);
			
		}else{
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(31,"Unable to save Job details"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
	}
		
	
	@RequestMapping(value="/getAllJobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId==null){
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}
		
		UsersDetails user = usersService.getUserById(userId);
		
		List<JobDetail> jobList = jobsService.getAllJobs();
		
		if(jobList!=null){
			
			return new ResponseEntity<List<JobDetail>>(jobList,HttpStatus.OK);
			
		}else{
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(31,"Unable to fetch Job details"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}	
	
	@RequestMapping(value="/getJob/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(@PathVariable("jobId") int jobId,HttpSession session){
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		if(userId==null){
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(7,"User session details not found"),HttpStatus.UNAUTHORIZED);
		}
		
		UsersDetails user = usersService.getUserById(userId);
		
		JobDetail job = jobsService.getJobs(jobId);
		
		if(job!=null){
			
			return new ResponseEntity<JobDetail>(job,HttpStatus.OK);
			
		}else{
			
			return new ResponseEntity<CollabApplicationError>(new CollabApplicationError(31,"Unable to fetch Job details"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}
}
