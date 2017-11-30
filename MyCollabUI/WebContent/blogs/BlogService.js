app.factory('BlogService',BlogService);
var BASE_URL = 'http://localhost:8181/MyCollab';
BlogService.$inject = ['$http', '$cookieStore', '$rootScope'];

function BlogService($http, $cookieStore, $rootScope){
	
	var service = {}
	
	service.createBlog = createBlog;
	service.viewBlogs = viewBlogs;
	
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
	
}