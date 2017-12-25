/**
 * Friend Controller
 */

app.controller('FriendController',FriendController);

function FriendController($scope,$location,FriendService,$rootScope){
	console.log("Friend Controller")
	
	function listOfSuggestedUsers() {
		
		FriendService.listOfSuggestedUsers()
		.then(function(response){
			console.log(response.data)
			$scope.suggesteduserList = response.data;
			
		},function(response){
			
			console.log(response.data.status)
			
		})
	}
	
	$scope.friendRequest = function(toUserId){
		
		FriendService.friendRequest(toUserId)
		.then(function(response){
			
			alert("Friend request to "+response.data.firstName+" has been sent successfully...");
			$location.path("/suggestedUsers");
			
		},function(response){
			console.log(response.data)
		})
	}
	
	function pendingRequests(){
		console.log("inside pending request")
		FriendService.pendingRequest()
		.then(function(response){
			console.log("inside success")
			$scope.pendingRequestList = response.data;			
		},function(response){
			console.log("inside failure")
			console.log(response.data)
		})
	}
	
	$scope.updatePendingRequest = function(fromId,status){
		
		FriendService.updatePendingRequest(fromId,status)
		.then(function(response){
			
			alert("Friend request to "+response.data.firstName+" has been updated successfully...");
						
		},function(response){
			console.log(response.data)
		})
		
		pendingRequests();
	}
	
	function listOfFriends(){
		console.log("inside list of friends")
		FriendService.listOfFriends()
		.then(function(response){
			
			console.log("inside success")
			$scope.friendList = response.data;	
			console.log(response.data)
			friendNames(response.data);
		},function(response){
			
			console.log("inside failure")
			console.log(response.data)
		})
	}
	
	
	function friendNames(friendList){
		console.log("inside list of friends Names")
		FriendService.friendNames(friendList)
		.then(function(response){
			
			console.log("inside success")
			console.log(response.data)
			$scope.friendNames = response.data;	
		},function(response){
			
			console.log("inside failure")
			console.log(response.data)
		})
	}
		listOfSuggestedUsers();
	
		pendingRequests();
	
		listOfFriends();
	
}