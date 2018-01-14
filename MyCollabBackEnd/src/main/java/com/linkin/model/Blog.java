package com.linkin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table
public class Blog implements Serializable{


		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int blogId;
		
		private String blogTitle;
		
		@Lob
		private String blogContent;
		
		Date createdDate;
		
		private int userId;
		
		private String userName;
		
		private String status;
		
		private int noOfLikes;
			
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy@HH:mm:ss")
		Date publishDate;

		public int getBlogId() {
			return blogId;
		}

		public void setBlogId(int blogId) {
			this.blogId = blogId;
		}

		public String getBlogTitle() {
			return blogTitle;
		}

		public void setBlogTitle(String blogTitle) {
			this.blogTitle = blogTitle;
		}

		public String getBlogContent() {
			return blogContent;
		}

		public void setBlogContent(String blogContent) {
			this.blogContent = blogContent;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getNoOfLikes() {
			return noOfLikes;
		}

		public void setNoOfLikes(int noOfLikes) {
			this.noOfLikes = noOfLikes;
		}

		public Date getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		
}
