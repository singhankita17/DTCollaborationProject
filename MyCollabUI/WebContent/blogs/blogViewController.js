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
	$scope.isRejected=false;
	$scope.editCommentFlag = false;
	function viewBlogDetails(blogId){
	   
	   BlogService.viewBlogById(blogId,function(response){
		   console.log("view blog by Id")
			 if (response.success) {
				 console.log( response.data)
		 		$scope.blog = response.data;
		 		
		 	}else{
		 		
		 		$scope.error = response.data;
		 		console.log( response.data)	
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 	}
		 })
		 
		 retrieveComment(blogId)
	}
	
	function viewPendingBlogDetails(blogId){
		   
		   BlogService.viewBlogById(blogId,function(response){
			   console.log("view blog by Id")
				 if (response.success) {
					 console.log( response.data)
			 		$scope.blogPost = response.data;
			 		
			 	}else{
			 		
			 		$scope.error = response.data;
			 		console.log( response.data)	
			 		if(response.status==401){
	     				$location.path("/login");
	     			}
			 	}
			 })
			 
			 retrieveComment(blogId)
	}
	
	$scope.showRejectionTxt=function(val){
			$scope.isRejected=val;
	}
	
	viewBlogDetails(blogId);
	if($rootScope.globals.currentUser!=undefined){
		if($rootScope.globals.currentUser.role === 'ADMIN'){
			viewPendingBlogDetails(blogId);
		}
	}
	
	$scope.addComment = function(comment,blogId){
		
		comment.blogId = blogId;
		 $scope.comment.userId = $rootScope.globals.currentUser.userId;
		 $scope.comment.username = $rootScope.globals.currentUser.username;
		BlogCommentService.addComment(comment)
		 .then(function(response){
			 console.log(response.data)
			 $scope.comment = {};
			 $scope.showCommentBlock = false;			
			 retrieveComment(blogId);
			 $scope.commentForm.$setPristine();
			 $scope.commentForm.$setUntouched();
			 
			 
		 },function(response){
			 console.log(response.data);
			 if(response.status==401){
  				$location.path("/login");
  			}
			 retrieveComment(blogId);
		 })
		 
		
		 
	}
	
	
	
	 $scope.editComment = function(commentObj){
		 $scope.editCommentFlag = false;
			if($rootScope.globals.currentUser != undefined){

				 $scope.comment = commentObj;
				 $scope.editCommentFlag = true;
				$scope.showCommentBlock = true;
				
			}else{
				
				$scope.showCommentBlock = false;
				console.log("redirect to login");
				$location.path("/login");
				
			}
		
	}
	 
	 
	 $scope.updateComment = function(commentObj){
		
			if($rootScope.globals.currentUser != undefined){

				 $scope.editCommentFlag = false;
				$scope.showCommentBlock = false;
				BlogCommentService.updateComment(commentObj)
				 .then(function(response){
					 console.log(response.data)	
					 $scope.comment = {};
					 retrieveComment(response.data.blogId);
					 $scope.editCommentFlag = false;
					 $scope.showCommentBlock = false;
					 $scope.commentForm.$setPristine();
					 $scope.commentForm.$setUntouched();
					 				 
				 },function(response){
					 console.log(response.data);
					 if(response.status==401){
		  				$location.path("/login");
		  			}
					 retrieveComment(blogId);
				 })
				
				
			}else{
				
				$scope.showCommentBlock = false;
				console.log("redirect to login");
				$location.path("/login");
				
			}
		
	}
	 
	 
	 $scope.deleteComment = function(commentObj){
			
			if($rootScope.globals.currentUser != undefined){

				$scope.editCommentFlag = false;
				$scope.showCommentBlock = false;
				BlogCommentService.deleteComment(commentObj)
				 .then(function(response){
					 
					 console.log(response.data);					
					 retrieveComment(commentObj.blogId);
									 				 
				 },function(response){
					 console.log(response.data);
					 if(response.status==401){
		  				$location.path("/login");
		  			}
					
				 })
				
				
			}else{
				
				console.log("redirect to login");
				$location.path("/login");
				
			}		
	}
	
	function retrieveComment(blogId){	
		 
		 BlogCommentService.retrieveComment(blogId)
		 .then(function(response){
			 console.log("retrieve comment")
			 console.log(response.data)
			 $scope.commentList = response.data;
		 },function(response){
			 
			 console.log(response.data)
			 if(response.status==401){
  				$location.path("/login");
  			}
		 })
	}
	
	
	$scope.updateLikes = function(blog){
		console.log(blog);
		/*console.log($scope.clickCount)
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
		}*/
		
		BlogService.updateNoOfLikes(blog,function(response){
			   console.log("update likes")
				 if (response.success) {
					 console.log( response.data)
			 		$scope.blog.noOfLikes = response.data.noOfLikes;
			 		
			 	}else{
			 		
			 		$scope.error = response.data;
			 		console.log( response.data)	
			 		if(response.status==401){
	     				$location.path("/login");
	     			}
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