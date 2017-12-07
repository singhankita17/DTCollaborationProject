app.factory('BlogService',BlogService);
var BASE_URL = 'http://localhost:8181/MyCollab';
BlogService.$inject = ['$http', '$cookieStore', '$rootScope'];

function BlogService($http, $cookieStore, $rootScope){
	
	var service = {}
	
	service.createBlog = createBlog;
	service.viewBlogs = viewBlogs;
	service.viewBlogById = viewBlogById;
	service.updateNoOfLikes = updateNoOfLikes;
	service.viewPendingBlogs = viewPendingBlogs;
	service.approveBlog = approveBlog;
	service.rejectBlog = rejectBlog;
	service.viewAllBlogs = viewAllBlogs;
	service.viewUserBlogs = viewUserBlogs;
	service.deleteBlog = deleteBlog;
	service.updateBlog = updateBlog;
	
	return service;
	
	function createBlog(blog,callback){
		
		$http.post(BASE_URL+"/createBlog",blog).then(function(response){
	
			console.log("response data: "+response.data)
    			if(response!=null){
    				 
    				 response = { success: true, data: response.data };
                } else {
                	 
                    response = { success: false, message: 'Blog creation failed' };
                }
                    callback(response);
		},function(response){
			console.log(response.data)
    			if(response!=null){
    				 
    				 response = { success: false, message: 'Blog creation failed' };
                }
			
                    callback(response);
		})
	}
	
	function viewBlogs(callback){
		
		$http.get(BASE_URL+"/viewBlogs").then(function(response){
	
			console.log("response data: "+response.data)
    			if(response!=null){
    				 
    				 response = { success: true, data: response.data };
                } else {
                	 
                    response = { success: false, message: 'Blog Search failed' };
                }
                    callback(response);
		},function(response){
			console.log(response.data)
    			if(response!=null){
    				 
    				 response = { success: false, message: 'Fetching blog details failed' };
                }
			
                    callback(response);
		})
	}
	
	
	function viewBlogById(blogId,callback){
		
		$http.get(BASE_URL+"/viewBlogById/"+blogId).then(function(response){
	
			console.log("response data: "+response.data)
    			if(response!=null){
    				 
    				 response = { success: true, data: response.data };
                } else {
                	 
                    response = { success: false, message: 'Blog Search failed' };
                }
                    callback(response);
		},function(response){
			console.log(response.data)
    			if(response!=null){
    				 
    				 response = { success: false, message: 'Fetching blog details failed' };
                }
			
                    callback(response);
		})
	}
	
	function updateNoOfLikes(blog, callback){
		
		$http.post(BASE_URL+"/updateLikes",blog).then(function(response){
			
			console.log("response data: "+response.data)
    			if(response!=null){
    				 
    				 response = { success: true, data: response.data };
                } else {
                	 
                    response = { success: false, message: 'Likes updation failed' };
                }
                    callback(response);
		},function(response){
			console.log(response.data)
    			if(response!=null){
    				 
    				 response = { success: false, message: 'Likes updation failed' };
                }
			
                    callback(response);
		})
	}
	
function viewPendingBlogs(callback){
		
		$http.get(BASE_URL+"/viewPendingBlogs").then(function(response){
	
			console.log("response data: "+response.data)
    			if(response!=null){
    				 
    				 response = { success: true, data: response.data };
                } else {
                	 
                    response = { success: false, message: 'Blog Search failed' };
                }
                    callback(response);
		},function(response){
			console.log(response.data)
    			if(response!=null){
    				 
    				 response = { success: false, message: 'Fetching blog details failed' };
                }
			
                    callback(response);
		})
	}

function viewAllBlogs(callback){
	
	$http.get(BASE_URL+"/viewAllBlogs").then(function(response){

		console.log("response data: "+response.data)
		
			if(response!=null){
				 
				 response = { success: true, data: response.data };
            } else {
            	 
                response = { success: false, message: 'Blog Search failed' };
            }
                callback(response);
	},function(response){
		console.log(response.data)
			if(response!=null){
				 
				 response = { success: false, message: 'Fetching blog details failed' };
            }
		
                callback(response);
	})
}

function viewUserBlogs(callback){
	
	$http.get(BASE_URL+"/viewUserBlogs").then(function(response){

		console.log("response data: "+response.data)
		
			if(response!=null){
				 
				 response = { success: true, data: response.data };
            } else {
            	 
                response = { success: false, message: 'Blog Search failed' };
            }
                callback(response);
	},function(response){
		console.log(response.data)
			if(response!=null){
				 
				 response = { success: false, message: 'Fetching blog details failed' };
            }
		
                callback(response);
	})
}

function approveBlog(blogId,callback){
	
	$http.get(BASE_URL+"/approveBlog/"+blogId).then(function(response){

		console.log("response data: "+response.data)
			if(response!=null){
				 
				 response = { success: true, data: response.data };
            } else {
            	 
                response = { success: false, message: 'Blog Approval failed' };
            }
                callback(response);
	},function(response){
		console.log(response.data)
			if(response!=null){
				 
				 response = { success: false, message: 'Approving Blog status failed' };
            }
		
                callback(response);
	})
}

function rejectBlog(blogId,callback){
	
	$http.get(BASE_URL+"/rejectBlog/"+blogId).then(function(response){

		console.log("response data: "+response.data)
			if(response!=null){
				 
				 response = { success: true, data: response.data };
            } else {
            	 
                response = { success: false, message: 'Blog Rejection failed' };
            }
                callback(response);
	},function(response){
		console.log(response.data)
			if(response!=null){
				 
				 response = { success: false, message: 'Reject Blog status failed' };
            }
		
                callback(response);
	})
}


function deleteBlog(blogId,callback){
	$http.delete(BASE_URL+"/deleteBlogById/"+blogId).then(function(response){
		
		console.log("response data: "+response.data)
			if(response!=null){
				 
				 response = { success: true, data: response.data };
            } else {
            	 
                response = { success: false, message: 'Blog Search failed' };
            }
                callback(response);
	},function(response){
		console.log(response.data)
			if(response!=null){
				 
				 response = { success: false, message: 'Fetching blog details failed' };
            }
		
                callback(response);
	})
}

function updateBlog(blog,callback){
	
	$http.put(BASE_URL+"/updateBlog",blog).then(function(response){

		console.log("response data: "+response.data)
			if(response!=null){
				 
				 response = { success: true, data: response.data };
            } else {
            	 
                response = { success: false, message: 'Blog updation failed' };
            }
                callback(response);
	},function(response){
		console.log(response.data)
			if(response!=null){
				 
				 response = { success: false, message: 'Blog updation failed' };
            }
		
                callback(response);
	})
}

}