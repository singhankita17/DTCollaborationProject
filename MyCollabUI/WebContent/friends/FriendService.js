/**
 * FriendService.js
 */

app.factory('FriendService',function($http,$cookieStore,$rootScope){
	
	var BASE_URL = 'http://localhost:8181/MyCollab';
	
	var friendService = {}
	
	friendService.listOfSuggestedUsers = listOfSuggestedUsers;
	friendService.friendRequest = friendRequest;
	friendService.pendingRequest = pendingRequest;
	friendService.updatePendingRequest = updatePendingRequest;
	friendService.listOfFriends = listOfFriends;
	friendService.deleteFriend = deleteFriend;
	
	function listOfSuggestedUsers(){
		return $http.get(BASE_URL+"/suggestedUsersList");
	}
	
	function friendRequest(toUserId){
		
		return $http.get(BASE_URL+"/friendRequest/"+toUserId);
	}
	
	function pendingRequest(){
		
		return $http.get(BASE_URL+"/pendingFriendRequest")
	}
	
	function updatePendingRequest(fromId,status){
		
		return $http.get(BASE_URL+"/updateFriendRequest/"+fromId+"/"+status)
	}
	
	function listOfFriends(){
		
		return $http.get(BASE_URL+"/listOfFriends")
	}
	
	function deleteFriend(friendId){
		
		return $http.get(BASE_URL+"/unfriend/"+friendId)
	}
	
	return friendService;
})
