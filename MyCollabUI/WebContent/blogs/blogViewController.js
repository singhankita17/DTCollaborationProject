/**
 * Blog View Controller
 */

app.controller('blogViewController',blogViewController);

blogController.$inject = ['$scope','$location','BlogService','BlogCommentService','$rootScope','$routeParams'];


function blogViewController($scope,$location,BlogService,BlogCommentService,$rootScope,$routeParams){
	
	console.log("Inside Blog View Controller")
	console.log($routeParams.id)
	var blogId = $routeParams.id;
	$rootScope.clickCount = 0;
	function viewBlogDetails(blogId){
	   
	   BlogService.viewBlogById(blogId,function(response){
		   console.log("view blog by Id")
			 if (response.success) {
				 console.log( response.data)
		 		$scope.blog = response.data;
		 		
		 	}else{
		 		
		 		$scope.error = response.data;
		 		console.log( response.data)	
		 	}
		 })
		 
		 retrieveComment(blogId)
	}
	
	
	viewBlogDetails(blogId);
	
	$scope.addComment = function(comment,blogId){
		
		comment.blogId = blogId;
		 $scope.comment.userId = $rootScope.globals.currentUser.userObj.c_user_id;
		 $scope.comment.username = $rootScope.globals.currentUser.userObj.username;
		BlogCommentService.addComment(comment)
		 .then(function(response){
			 console.log(response.data)
			 $scope.comment = '';
		 },function(response){
			 console.log(response.data)
		 })
		 
		 retrieveComment(blogId)
		 
	}
	
	function retrieveComment(blogId){	
		 
		 BlogCommentService.retrieveComment(blogId)
		 .then(function(response){
			 console.log("retrieve comment")
			 console.log(response.data)
			 $scope.commentList = response.data;
		 },function(response){
			 
			 console.log(response.data)
		 })
	}
	
	
	$scope.updateLikes = function(blog){
		
		console.log($scope.clickCount)
		if($rootScope.clickCount == 0){
			blog.noOfLikes = blog.noOfLikes + 1;
			$scope.clickCount = 1;
			$rootScope.clickCount = 1;
		}
		else
		{
			blog.noOfLikes = blog.noOfLikes - 1;
			$scope.clickCount = 0;
			$rootScope.clickCount = 0;
		}
		
		BlogService.updateNoOfLikes(blog,function(response){
			   console.log("update likes")
				 if (response.success) {
					 console.log( response.data)
			 		$scope.blog.noOfLikes = response.data.noOfLikes;
			 		
			 	}else{
			 		
			 		$scope.error = response.data;
			 		console.log( response.data)	
			 	}
		})
	}
}