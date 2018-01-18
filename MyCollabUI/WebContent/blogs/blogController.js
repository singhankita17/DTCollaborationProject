app.controller("blogController",blogController);

blogController.$inject = ['$scope','$location','BlogService','BlogCommentService','$rootScope'];

function blogController($scope,$location,BlogService,BlogCommentService,$rootScope){
	
	 console.log("Blog controller")
		 
     $scope.errormessage=''
    	 
    $scope.editBlogFlag = false;
   	 
     $scope.createNewBlog = function (){
    	 
		/* $scope.blog.userId = $rootScope.globals.currentUser.userObj.c_user_id;
		
    	 console.log($scope.blog.userId)*/
    	
		 BlogService.createBlog($scope.blog,function(response){
			 
			 if (response.success) {
				
		 		console.log("Blog created Successfully")
		 		alert("Blog created Successfully")
		 		$scope.blog={}
		 		viewUserBlogs();
		 		
		 	}else{
		 		
		 		$scope.errormessage = response.data.errormessage;
		 		console.log($scope.errormessage)
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		$location.path("/createBlog")
		 	}
		 })
	 }
	 
	 function viewApprovedBlogs(){
		
		 BlogService.viewApprovedBlogs(function(response){
			 
			 if (response.success) {
				 $scope.commentCountList = [];
			 		$scope.blogList = response.data;
			 		 angular.forEach($scope.blogList, function(item){
		                   console.log(item.blogId);  
		                   BlogCommentService.retrieveComment(item.blogId)
		          		 .then(function(response){
		          			 var obj = {};
		          			 var valObj = {};
		          			 $scope.commentList = response.data;
		          			 valObj.length = response.data.length
		          			 $scope.commentCountList[item.blogId] = valObj;
		          			
		          			 console.log("Comment on BlogId "+item.blogId+" = "+$scope.commentCountList[item.blogId].length)
		          		 },function(response){
		          			if(response.status==401){
		         				$location.path("/login");
		         			}
		          			 console.log(response.data)
		          		 })
		               })
		 		
		 	}else{
		 		
		 		console.log( response.data)
		 		
		 	}
		 })
	 }
	 
	 viewApprovedBlogs();
	
	 $scope.approveOrRejectBlog = function(blogId,status,rejectionReason){
		 
		 if(status === 'APPROVED'){
			 console.log("Approved")
			 BlogService.approveBlog(blogId,function(response){
			 
			 if (response.success) {
				
		 		console.log(response.data);
		 		 getAllPendingBlogs();
				 viewAllBlogs(); 
				 alert("Blog approved Successfully");
				 $location.path("/admin/manageBlog")
		 		
		 	}else{
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		$scope.error = response.data;
		 		console.log( response.data)
		 		
		 	}
		 })
			 
		 }else if(status === 'REJECTED'){
			 console.log("Rejected")
			  BlogService.rejectBlog(blogId,rejectionReason,function(response){
			 
			 if (response.success) {
				
		 		console.log(response.data);
		 		 getAllPendingBlogs();
				 viewAllBlogs(); 
				 $location.path("/admin/manageBlog")
		 	}else{
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		console.log( response.data)
		 		
		 	}
		 })
		 }
		
		
	 }
	 
	 function getAllPendingBlogs(){
		 
		if($rootScope.globals.currentUser){
		 BlogService.viewPendingBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogPendingList = response.data;
		 		
		 	}else{
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		console.log( response.data)
		 		
		 	}
		 })
		}
	 }
	 
	 function viewAllBlogs(){
			
		 BlogService.viewAllBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogAllList = response.data;
		 		
		 	}else{
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		console.log( response.data)
		 		
		 	}
		 })
	 }
	 
	 function viewUserBlogs(){
		 
		if($rootScope.globals.currentUser){			 
		 BlogService.viewUserBlogs(function(response){
			 
			 if (response.success) {
				console.log("Fetch User Blogs")
		 		$scope.blogUserList = response.data;
		 		console.log($scope.blogUserList)
		 	}else{
		 		if(response.status==401){
     				$location.path("/login");
     			}
		 		console.log( response.data)
		 		
		 	}
		 })
		}
	 }
	 
	 $scope.deleteBlog = function(blogId){
		 
		 BlogService.deleteBlog(blogId, function(response){
			 
			 if (response.success) {
				
		 		console.log(response.data);
		 		 
		 		viewUserBlogs(); 
		 		
		 	 }
			 else
			 {
				 if(response.status==401){
	     				$location.path("/login");
	     			}
		 		console.log( response.data)
		 		
		 	}
		 })
		 
	 }
	 
	 
	 $scope.editBlog = function(blog){		
				
		 		console.log(blog);
		 		$scope.blog = blog;
		 		$scope.editBlogFlag = true;
		 		viewUserBlogs(); 
		 				 
	 }
	 
	 
	 $scope.updateBlog = function(blog){
		 
		 BlogService.updateBlog(blog, function(response){
			 
			 if (response.success) {
				
		 		console.log(response.data);
		 		$scope.blog = {};
		 		$scope.editBlogFlag = false;
		 		viewUserBlogs(); 
		 		
		 	}
			 else
			 {
				 if(response.status==401){
	     				$location.path("/login");
	     			}
		 		console.log( response.data)
		 		
		 	}
		 })
		 
	 }
	 
	 getAllPendingBlogs();
	 
	 viewAllBlogs();
	 
	 viewUserBlogs();
}