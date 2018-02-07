package com.linkin.dao;

import com.linkin.model.Blog;
import com.linkin.model.BlogPostLikes;
import com.linkin.model.UsersDetails;

public interface BlogPostLikesDao {

	public BlogPostLikes getUserBlogPostLikes(Blog blog,UsersDetails user);
	
	public Blog updateBlogPostLikes(Blog blog,UsersDetails user);
	
}
