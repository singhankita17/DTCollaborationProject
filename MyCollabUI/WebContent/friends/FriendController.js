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
	
	listOfSuggestedUsers();
}