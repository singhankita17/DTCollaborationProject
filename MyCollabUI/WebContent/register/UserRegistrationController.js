app.controller('userRegistrationController',function($scope,$route,$location,$rootScope,userService){
	
	   console.log("invoke user edit profile in controller");
	   
	   if($rootScope.currentuser != undefined){
		   alert($rootScope.currentuser);
		   userService.retrieveUserByUserId($rootScope.currentuser.c_user_id).then(function(response){
	    		
				$scope.user = response.data;
			},
			function(response){
				if(response.status == 401){
					$location.path("/login")
				}
				console.log(response.data.errorMessage);
				$scope.error = response.data;
			})
	   }

		console.log("invoked register function in controller");
		
	    $scope.register=function(){
	    	
	    	userService.registerUser($scope.user).then(function(response){
	    		console.log("User registered successfully");
				$scope.errormessage = "User registered successfully";
				$location.path ('/login');
			},
			function(response){
				console.log("This UserName or Email is already registered");
				$scope.errormessage = "This UserName or Email is already registered";
				$location.path('/register');
			})
	  }
	    
	  $scope.edituserprofile = function(){
		  
		  userService.edituserprofile($scope.user).then(function(response){
	    		alert("User updated successfully")
				$location.path ('/home');
			},
			function(response){
				if(response.status == 401){
					$location.path("/login")
				}
				console.log(response.data.errorMessage);
				$scope.error = response.data;
			})
	  }
});