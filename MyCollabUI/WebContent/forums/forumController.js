app.controller("forumController",forumController);

function forumController($scope,$location,ForumService,ForumCommentService,$rootScope,$routeParams){
		
		console.log("Forum Controller")
		
		var forumParamId = $routeParams.id;
		$scope.isRejected=false;
		
	$scope.createNewForum = function (){		
		  	 console.log("Create New Forum")
    	  ForumService.createForum($scope.forum)
    	  .then(function(response){
			 
				$scope.errormessage = "Forum created Successfully"
		 		console.log($scope.errormessage)
		 		alert($scope.errormessage)
		 		$scope.forumCreationForm.$setPristine();
				$scope.forumCreationForm.$setUntouched();
		 		$location.path("/home")
    	  },function(response){		 		
		 		$scope.errormessage = response.data.errorMessage;
		 		console.log($scope.errormessage)
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		$location.path("/createForum")
		 });
		
	}
		
	function viewAllForums (){
		 $scope.commentCountList = [];
		 console.log("viewAllForums")
		ForumService.viewAllForums()
		 .then(function(response){
				//alert(response.data)
		 		console.log(response.data)
		 		$scope.forumList = response.data;
		 		
		 		 angular.forEach($scope.forumList, function(item){
	                   console.log(item.forumId);  
	                   ForumCommentService.retrieveComment(item.forumId)
	          		 .then(function(response){
	          			 var obj = {};
	          			 var valObj = {};
	          			 $scope.forumCommentList = response.data;
	          			 valObj.length = response.data.length
	          			 $scope.commentCountList[item.forumId] = valObj;
	          			
	          			 console.log("Comment on ForumId "+item.forumId+" = "+$scope.commentCountList[item.forumId].length)
	          		 },function(response){
	          			 
	          			 console.log(response.data)
	          			
	          		 })
	               })
		 		
		 		
 	  },function(response){		 		
 		 //alert(response.data)
		 		$scope.errormessage = response.data.errorMessage;
		 		console.log($scope.errormessage)
		 		$location.path("/viewForum")
		 });
		
		
	}
	
	
	
	function viewForumDetail(forumId){
		
		 console.log("viewForumDetail")
		if(forumId){
			console.log("Inside viewForumDetail")
		ForumService.viewForumDetail(forumId)
		 .then(function(response){
				
		 		console.log(response.data)
		 		$scope.forum = response.data;
		 		retrieveForumComment($scope.forum.forumId);
		 		
		 },function(response){		 	
			    
		 		$scope.errormessage = response.data.errorMessage;
		 		console.log($scope.errormessage)
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		$location.path("/viewForumDetail")
		 });
		}
		 
	}
	
	
	function viewPendingForumDetail(forumId){
		
		 console.log("Pending viewPendingForumDetail")
		if(forumId){
			console.log("Inside Pending viewForumDetail")
		ForumService.viewForumDetail(forumId)
		 .then(function(response){
				
		 		console.log(response.data)
		 		$scope.forumPost = response.data;
		 		
		 },function(response){		 	
			    
		 		$scope.errormessage = response.data.errorMessage;
		 		console.log($scope.errormessage)
		 		if(response.status==401){
    				$location.path("/login");
    			}
		 		$location.path("/viewForumDetail")
		 });
		}
		 
	}
	
	function viewAllUsersForums(){
		
		 if($rootScope.globals.currentUser){
			 
			 ForumService.viewAllUsersForums()
			 .then(function(response){
					
			 		$scope.forumAllList = response.data;
			 		
			 	},function(response){
			 		
			 		console.log( response.data)
			 		if(response.status==401){
	     				$location.path("/login");
	     			}
			 		
			 	})
			 }
	}
	 
	 
	 function viewAllPendingForums(){
			
		 if($rootScope.globals.currentUser){
			 
			 ForumService.viewAllPendingForums()
			 .then(function(response){
					
			 		$scope.forumPendingList = response.data;
			 		
			 	},function(response){
			 		
			 		console.log( response.data)
			 		if(response.status==401){
	     				$location.path("/login");
	     			}
			 		
			 	})
			 }
	}
	
	viewAllForums();
	
	viewUserForums();
	viewAllUsersForums();
	viewAllPendingForums();
	if(forumParamId!==undefined){
		viewForumDetail(forumParamId);
		viewPendingForumDetail(forumParamId);
	}
	
	
	
	$scope.addForumComment = function(comment,forumId){
		
		comment.forumId = forumId;
		 $scope.comment.userId = $rootScope.globals.currentUser.userId;
		 $scope.comment.username = $rootScope.globals.currentUser.username;
		ForumCommentService.addComment(comment)
		 .then(function(response){
			 console.log(response.data)
			 $scope.comment = '';
			 $scope.showCommentBlock = false;	
			 retrieveForumComment(response.data.forumId);
			 $scope.commentForm.$setPristine();
			 $scope.commentForm.$setUntouched();
			
		 },function(response){
			 console.log(response.data)
			 if(response.status==401){
  				$location.path("/login");
  			}
		 })
		 
		 retrieveForumComment(forumId)
	}
	
	
	function retrieveForumComment(forumId){	
		 $scope.commentList = {};
		
		 if(forumId){
		 ForumCommentService.retrieveComment(forumId)
		 .then(function(response){
			 
			 console.log("retrieve comment")
			 console.log(response.data)
			 $scope.commentList = response.data;
		 },function(response){
			 
			 console.log(response.data)
		 })
	}
	}
	
	function viewUserForums(){
		
		 if($rootScope.globals.currentUser){
			 
			 ForumService.viewUserForums()
			 .then(function(response){
					
			 		$scope.forumUserList = response.data;
			 		
			 	},function(response){
			 		
			 		console.log( response.data)
			 		if(response.status==401){
	     				$location.path("/login");
	     			}
			 	})
			 }
	}
	
	
	$scope.editForum = function(forum){		
		
 		console.log(forum);
 		$scope.forum = forum;
 		$scope.editForumFlag = true;
 		viewUserForums(); 
 				 
	}

	$scope.updateForum = function(forum){
 
	ForumService.updateForum(forum)
	.then(function(response){		
 		console.log(response.data);
 		$scope.forum = {};
 		$scope.editForumFlag = false;
 		viewUserForums(); 
 		$scope.forumCreationForm.$setPristine();
		$scope.forumCreationForm.$setUntouched();
 		
 	},function(response){
 		
 		console.log( response.data)
 		if(response.status==401){
				$location.path("/login");
		}
 	})
 
	}
	
	

	 $scope.deleteForum = function(forumId){
		 
		 ForumService.deleteForum(forumId)
		 .then(function(response){
			 
		 		console.log(response.data);
		 		 
		 		viewUserForums(); 
		 },function(response){	
		 		console.log( response.data)
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 })
		 
	 }
	 
	 $scope.approveOrRejectForum = function(forumId,status,rejectionReason){
		 
		 if(status === 'APPROVED'){
			 ForumService.approveForum(forumId)
			 .then(function(response){
			 
		 		console.log(response.data);
		 		
		 		viewAllPendingForums();
		 		viewAllUsersForums();
		 		 alert("Forum approved Successfully");
				 $location.path("/admin/manageForum");
				 },function(response){	
				 		console.log( response.data)
				 		if(response.status==401){
		     				$location.path("/login");
		     			}
				 })
		 }else if(status === 'REJECTED'){
			 ForumService.rejectForum(forumId,rejectionReason)
			 .then(function(response){
			 
		 		console.log(response.data);
		 		
		 		viewAllPendingForums();
		 		viewAllUsersForums();
		 		 $location.path("/admin/manageForum");
				 },function(response){	
				 		console.log( response.data)
				 		if(response.status==401){
		     				$location.path("/login");
		     			}
				 })
			 
		 }
	 }

	 $scope.showRejectionTxt=function(val){
			$scope.isRejected=val;
	}
	 
	 $scope.accessCommentBlock = function(){
			$scope.showCommentBlock = false;
			console.log( $rootScope.globals.currentUser);
			
			if($rootScope.globals.currentUser != undefined){
				$scope.showCommentBlock = true;
				
			}else{
				
				$scope.showCommentBlock = false;
				console.log("redirect to login");
				$location.path("/login");
				
			}
			
		}
}