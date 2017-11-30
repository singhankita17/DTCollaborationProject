app.controller("forumController",forumController);

forumController.$inject = ['$scope','$location','ForumService','$rootScope'];

function forumController($scope,$location,ForumService,$rootScope){
		
	$scope.createNewForum = function (){
		
		$scope.blog.userId = $rootScope.globals.currentUser.userObj.c_user_id
    	 
    	 console.log($scope.blog.userId)
    	 
    	  ForumService.createForum($scope.forum,function(response){
			 
			 if (response.success) {
				
		 		$scope.errormessage = "Forum created Successfully"
		 		console.log($scope.errormessage)
		 		alert($scope.errormessage)
		 		$location.path("/home")
		 	}else{
		 		
		 		$scope.errormessage = response.data.errormessage;
		 		console.log($scope.errormessage)
		 		$location.path("/createForum")
		 	}
		 })
		
	}
}