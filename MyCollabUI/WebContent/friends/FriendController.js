/**
 * Friend Controller
 */

app.controller('FriendController',FriendController);

function FriendController($scope,$location,FriendService,$rootScope,$window){
	console.log("Friend Controller")
	
	function listOfSuggestedUsers() {
		
		FriendService.listOfSuggestedUsers()
		.then(function(response){
			console.log(response.data)
			$scope.suggesteduserList = response.data;
			
		},function(response){
			
			console.log(response.data.status)
			if(response.status==401){
				$location.path("/login");
		}
			
		})
	}
	
	$scope.friendRequest = function(toUserId){
		
		FriendService.friendRequest(toUserId)
		.then(function(response){
			
			alert("Friend request to "+response.data.firstName+" has been sent successfully...");
			listOfSuggestedUsers();
			$location.path("/suggestedUsers");
			
		},function(response){
			console.log(response.data)
			if(response.status==401){
				$location.path("/login");
		}
		})
	}
	
	function pendingRequests(){
		console.log("inside pending request")
		FriendService.pendingRequest()
		.then(function(response){
			console.log("inside pending success")
			console.log(response.data)
			$scope.pendingRequestList = response.data;	
			console.log($scope.pendingRequestList);
			
		},function(response){
			console.log("inside pending failure")
			console.log(response.data)
			if(response.status==401){
				$location.path("/login");
		}
		})
	}
	
	$scope.updatePendingRequest = function(friend,status){
		
		if(status === "REJECTED"){
			 if ($window.confirm("Are you sure you want to Reject this Friend ?")) {
				
             	FriendService.deleteFriend(friend.id)
         		.then(function(response){         			
         			console.log("inside delete friend success");
         			console.log(response.data)
         			pendingRequests();
         		},function(response){
         			
         			console.log("inside delete friend failure")
         			console.log(response.data)
         			if(response.status==401){
        				$location.path("/login");
         			}
         		})
             } else {
             	console.log("You clicked No")
                 $location.path("/pendingrequests")
             }
		}else if(status === "ACCEPTED"){
			FriendService.updatePendingRequest(friend.fromId,status)
			.then(function(response){
				
				alert("Friend request from "+response.data.firstName+" has been updated successfully...");
				pendingRequests();			
			},function(response){
				console.log(response.data)
				if(response.status==401){
					$location.path("/login");
				}
			})
		}
		//pendingRequests();
		
	}
	
	function listOfFriends(){
		console.log("inside list of friends")
		FriendService.listOfFriends()
		.then(function(response){
			
			console.log("inside success")
			$scope.friendList = response.data;	
			console.log(response.data)
		},function(response){
			
			console.log("inside failure")
			console.log(response.data)
			if(response.status==401){
				$location.path("/login");
			}
		})
	}
	
	$scope.deleteFriend = function(friend){
		console.log("inside unfriend request")
	
                if ($window.confirm("Are you sure you want to remove this Friend ?")) {
                	
                	FriendService.deleteFriend(friend.id)
            		.then(function(response){
            			
            			console.log("inside delete success")
            			$scope.friendList = response.data;	
            			console.log(response.data);
            			
            		},function(response){
            			
            			console.log("inside failure")
            			console.log(response.data)
            			if(response.status==401){
            				$location.path("/login");
            			}
            		})
                } else {
                	console.log("You clicked No")
                    $location.path("/friendList")
                }
            
		
		
	}
		
		listOfSuggestedUsers();
		
		listOfFriends();
	
		pendingRequests();
	
		
	
}