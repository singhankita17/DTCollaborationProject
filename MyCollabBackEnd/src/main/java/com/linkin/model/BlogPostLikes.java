package com.linkin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="blogpostlikes")
public class BlogPostLikes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Blog blog;
	
	@ManyToOne
	private UsersDetails user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public UsersDetails getUser() {
		return user;
	}

	public void setUser(UsersDetails user) {
		this.user = user;
	}

}
