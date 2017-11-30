app.controller("blogController",blogController);

blogController.$inject = ['$scope','$location','BlogService','$rootScope'];

function blogController($scope,$location,BlogService,$rootScope){
	
	 console.log("Blog controller")
		 
     $scope.errormessage=''
    	 
    	
    /*retrieveUser = function (){
		 
		 userService.retrieveUserByUserName()
		 		.then(function(response){
		 		console.log("User details retrieved")
		 		$scope.blog.userId = response.data.c_user_id;
		 		
		 	}, function(response){
		 		$scope.errormessage = response.data.errormessage;
		 		console.log($scope.errormessage)
		 		$location.path("/createBlog")
		 	})
	 }*/
    	 
     $scope.createNewBlog = function (){
    	 
    	 $scope.blog.userId = $rootScope.globals.currentUser.userObj.c_user_id
    	 
    	 console.log($scope.blog.userId)
    	 
		 BlogService.createBlog($scope.blog,function(response){
			 
			 if (response.success) {
				
		 		$scope.errormessage = "Blog created Successfully"
		 		console.log($scope.errormessage)
		 		alert($scope.errormessage)
		 		$location.path("/home")
		 	}else{
		 		
		 		$scope.errormessage = response.data.errormessage;
		 		console.log($scope.errormessage)
		 		$location.path("/createBlog")
		 	}
		 })
	 }
	 
	 function viewBlogs(){
		
		 BlogService.viewBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogList = response.data;
		 		
		 	}else{
		 		
		 		$scope.errormessage = response.data.errormessage;
		 		console.log($scope.errormessage)
		 		
		 	}
		 })
	 }
	 
	 viewBlogs();
}