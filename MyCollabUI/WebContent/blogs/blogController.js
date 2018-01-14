app.controller("blogController",blogController);

blogController.$inject = ['$scope','$location','BlogService','$rootScope'];

function blogController($scope,$location,BlogService,$rootScope){
	
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
		 		$location.path("/createBlog")
		 	}
		 })
	 }
	 
	 function viewApprovedBlogs(){
		
		 BlogService.viewApprovedBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogList = response.data;
		 		
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
		 		
		 	}else{
		 		
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
		 		
		 	}else{
		 		
		 		console.log( response.data)
		 		
		 	}
		 })
		 }
		
		
	 }
	 
	 function getAllPendingBlogs(){
		 
		 BlogService.viewPendingBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogPendingList = response.data;
		 		
		 	}else{
		 		
		 		console.log( response.data)
		 		
		 	}
		 })
	 }
	 
	 function viewAllBlogs(){
			
		 BlogService.viewAllBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogAllList = response.data;
		 		
		 	}else{
		 		
		 		console.log( response.data)
		 		
		 	}
		 })
	 }
	 
	 function viewUserBlogs(){
		 if($rootScope.isLogged){
			 
		 BlogService.viewUserBlogs(function(response){
			 
			 if (response.success) {
				
		 		$scope.blogUserList = response.data;
		 		
		 	}else{
		 		
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
		 		
		 		console.log( response.data)
		 		
		 	}
		 })
		 
	 }
	 
	 getAllPendingBlogs();
	 
	 viewAllBlogs();
	 
	 viewUserBlogs();
}