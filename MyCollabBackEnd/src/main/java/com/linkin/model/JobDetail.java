package com.linkin.model;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_JOBS")
public class JobDetail {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String jobProfile;
	
	private String jobDesc;
	
	private String qualification;
	
	private String status;
	
	private Date postDate;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobProfile() {
		return jobProfile;
	}

	public void setJobProfile(String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date date) {
		this.postDate = date;
	}
	
}
