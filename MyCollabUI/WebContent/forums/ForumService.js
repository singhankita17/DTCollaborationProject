app.factory("ForumService",ForumService);

var BASE_URL = 'http://localhost:8181/MyCollab';

function ForumService($http,$cookieStore,$rootScope){
	
	var service = {}
	
	service.createForum = createForum;
	service.viewAllForums = viewAllForums;
	service.viewForumDetail = viewForumDetail;
	service.viewUserForums = viewUserForums;
	service.deleteForum = deleteForum;
	service.updateForum = updateForum;
	service.viewAllUsersForums = viewAllUsersForums;
	service.viewAllPendingForums = viewAllPendingForums;
	service.approveForum  =  approveForum;
	service.rejectForum  =  rejectForum;
	
	function createForum(forum){
		
		return $http.post(BASE_URL+"/createForum",forum)
	}
	
	function viewAllForums(){
		return $http.get(BASE_URL+"/viewForums")
	}
	
	function viewForumDetail(forumId){
		
		return $http.get(BASE_URL+"/viewForumById/"+forumId)
	}
	
	function viewUserForums(){
		return $http.get(BASE_URL+"/viewUserForums")
	}
	
	function viewAllUsersForums(){
		return $http.get(BASE_URL+"/viewAllUsersForums")
	}
	
	function viewAllPendingForums(){
		
		return $http.get(BASE_URL+"/viewPendingForums")
	}
	
	function updateForum(forum){
		
		return $http.put(BASE_URL+"/updateForum",forum)
	}
	
	function deleteForum(forumId){
		
		return $http.delete(BASE_URL+"/deleteForumById/"+forumId)
	}

	
	
	function approveForum(forumId){
		
		return $http.get(BASE_URL+"/approveForum/"+forumId)
	}

	function rejectForum(forumId,rejectionReason){
		if(rejectionReason != undefined){
		
			return $http.get(BASE_URL+"/rejectForum/"+forumId+"?rejectionReason="+rejectionReason)
			
		}else {
			
			return $http.get(BASE_URL+"/rejectForum/"+forumId)
		}
	}
	return service;
}