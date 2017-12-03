/**
 * BlogCommentService
 */

app.factory('BlogCommentService',function($http,$cookieStore,$rootScope){
	
	var blogCommentService = {}
		
	blogCommentService.retrieveComment = function (blogId){
		
		return $http.get(BASE_URL+"/getBlogComment/"+blogId)
	}
	
	
	blogCommentService.addComment = function (comment){
		
		return $http.post(BASE_URL+"/addBlogComment",comment)
	}
	
	return blogCommentService;
})