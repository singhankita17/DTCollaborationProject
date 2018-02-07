package com.linkin.service;

import com.linkin.model.Blog;
import com.linkin.model.BlogPostLikes;
import com.linkin.model.UsersDetails;

public interface BlogPostLikesService {

	//if user already liked the post, 1 object
	//if user has not yet liked the post, null object
	public BlogPostLikes getUserBlogPostLikes(Blog blog,UsersDetails user);
	
	//increment / decrement the number of likes
	//insert into blogpostlikes / delete from blogpostlikes 
	public Blog updateBlogPostLikes(Blog blog,UsersDetails user);
}
