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
		 $scope.comment.userId = $rootScope.globals.currentUser.userId;
		 $scope.comment.username = $rootScope.globals.currentUser.username;
		BlogCommentService.addComment(comment)
		 .then(function(response){
			 console.log(response.data)
			 $scope.comment = '';
			 retrieveComment(blogId);
		 },function(response){
			 console.log(response.data);
			 retrieveComment(blogId);
		 })
		 
		
		 
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
		console.log(blog);
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