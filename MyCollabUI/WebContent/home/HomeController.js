app.controller('homeController',function($scope,$rootScope,$location,HomeService,BlogService,BlogCommentService){
	
	$scope.message="Welcome to My Collaboration Portal";
	
	/*function getNotifications(){
		
		HomeService.getNotificationNotViewed()
		.then(function(response){
			console.log(response.data)
			$rootScope.notificationNotViewed = response.data;
			$rootScope.notificationNotViewedLength = response.data.length;
		},function(response){
			console.log(response.data)
			if(response.status==401){
				$location.path("/login");
			}
		})
		
		
		HomeService.getNotificationViewed()
		.then(function(response){
			console.log(response.data)
			$rootScope.notificationViewed = response.data;
		},function(response){
			console.log(response.data)
			if(response.status==401){
				$location.path("/login");
			}
		})
	}
	
	getNotifications();
	
	$rootScope.updateLength = function(){
		$rootScope.notificationNotViewedLength = 0;
	}
	
	$rootScpe.updateNotification = function(notificationId){
		
		HomeService.updateNotification(notificationId).then(function(response){
			getNotification();
		},function(response){
			console.log(response.data)
			if(response.status==401){
				$location.path("/login");
			}
		})
	}
*/	
	
	
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
	          			 
	          			 console.log(response.data)
	          		 })
	               })
		 	}else{
		 		
		 		console.log( response.data)
		 		
		 	}
		 })
	 }
	 
	 viewApprovedBlogs();
});

