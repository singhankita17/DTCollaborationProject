/**
 * BlogCommentService
 */

app.factory('BlogCommentService',function($http,$cookieStore,$rootScope){
	
	var blogCommentService = {}
	
	var BASE_URL = 'http://localhost:8181/MyCollab';
		
	blogCommentService.retrieveComment = function (blogId){
		
		return $http.get(BASE_URL+"/getBlogComment/"+blogId)
	}
	
	
	blogCommentService.addComment = function (comment){
		
		return $http.post(BASE_URL+"/addBlogComment",comment)
	}
	
	return blogCommentService;
})