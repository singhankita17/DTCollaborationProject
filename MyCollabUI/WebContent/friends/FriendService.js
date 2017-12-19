/**
 * FriendService.js
 */

app.factory('FriendService',function($http,$cookieStore,$rootScope){
	
	var BASE_URL = 'http://localhost:8181/MyCollab';
	
	var friendService = {}
	
	friendService.listOfSuggestedUsers = listOfSuggestedUsers;
	friendService.friendRequest = friendRequest;
	
	function listOfSuggestedUsers(){
		return $http.get(BASE_URL+"/suggestedUsersList");
	}
	
	function friendRequest(toUserId){
		
		return $http.get(BASE_URL+"/friendRequest/"+toUserId);
	}
	
	return friendService;
})
