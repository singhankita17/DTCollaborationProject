app.controller("forumController",forumController);

function forumController($scope,$location,ForumService,ForumCommentService,$rootScope,$routeParams){
		console.log("Forum Controller")
		
		var forumId = $routeParams.id;
		
	$scope.createNewForum = function (){		
		  	 console.log("Create New Forum")
    	  ForumService.createForum($scope.forum)
    	  .then(function(response){
			 
				$scope.errormessage = "Forum created Successfully"
		 		console.log($scope.errormessage)
		 		alert($scope.errormessage)
		 		$location.path("/home")
    	  },function(response){		 		
		 		$scope.errormessage = response.data.errorMessage;
		 		console.log($scope.errormessage)
		 		$location.path("/createForum")
		 });
		
	}
		
	function viewAllForums (){
		
		 console.log("viewAllForums")
		ForumService.viewAllForums()
		 .then(function(response){
				//alert(response.data)
		 		console.log(response.data)
		 		$scope.forumList = response.data;
		 		
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
		 		
		 },function(response){		 	
			    
		 		$scope.errormessage = response.data.errorMessage;
		 		console.log($scope.errormessage)
		 		$location.path("/viewForumDetail")
		 });
		}
		 
		 retrieveForumComment(forumId)
	}
	
	function viewAllUsersForums(){
		
		 if($rootScope.globals.currentUser){
			 
			 ForumService.viewAllUsersForums()
			 .then(function(response){
					
			 		$scope.forumAllList = response.data;
			 		
			 	},function(response){
			 		
			 		console.log( response.data)
			 		
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
			 		
			 	})
			 }
	}
	
	viewAllForums();
	viewForumDetail(forumId);
	viewUserForums();
	viewAllUsersForums();
	viewAllPendingForums();
	
	$scope.addForumComment = function(comment,forumId){
		
		comment.forumId = forumId;
		 $scope.comment.userId = $rootScope.globals.currentUser.userObj.c_user_id;
		 $scope.comment.username = $rootScope.globals.currentUser.userObj.username;
		ForumCommentService.addComment(comment)
		 .then(function(response){
			 console.log(response.data)
			 $scope.comment = '';
		 },function(response){
			 console.log(response.data)
		 })
		 
		 retrieveForumComment(forumId)
	}
	
	
	function retrieveForumComment(forumId){	
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
 		
 	},function(response){
 		
 		console.log( response.data)
 
 	})
 
	}
	
	

	 $scope.deleteForum = function(forumId){
		 
		 ForumService.deleteForum(forumId)
		 .then(function(response){
			 
		 		console.log(response.data);
		 		 
		 		viewUserForums(); 
		 },function(response){	
		 		console.log( response.data)
		 })
		 
	 }
	 
	 $scope.approveOrRejectForum = function(forumId,status,rejectionReason){
		 
		 if(status === 'APPROVED'){
			 ForumService.approveForum(forumId)
			 .then(function(response){
			 
		 		console.log(response.data);
		 		
		 		viewAllPendingForums();
		 		viewAllUsersForums();
				 },function(response){	
				 		console.log( response.data)
				 })
		 }else if(status === 'REJECTED'){
			 ForumService.rejectForum(forumId,rejectionReason)
			 .then(function(response){
			 
		 		console.log(response.data);
		 		
		 		viewAllPendingForums();
		 		viewAllUsersForums();
				 },function(response){	
				 		console.log( response.data)
				 })
			 
		 }
	 }
	 
}