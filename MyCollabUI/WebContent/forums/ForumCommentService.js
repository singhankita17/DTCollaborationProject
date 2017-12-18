/**
 * ForumCommentService
 */

app.factory('ForumCommentService',function($http,$cookieStore,$rootScope){
	
	var forumCommentService = {}
	
	var BASE_URL = 'http://localhost:8181/MyCollab';
	
	forumCommentService.addComment = addComment;
	forumCommentService.retrieveComment = retrieveComment;
	
	function addComment(comment){
		
		return $http.post(BASE_URL+"/addForumComment",comment)
	}
	
	function retrieveComment(forumId){
		
		return $http.get(BASE_URL+"/getForumComment/"+forumId)
	}
	
	
	return forumCommentService;
})