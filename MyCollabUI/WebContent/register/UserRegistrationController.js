app.controller('userRegistrationController',function($scope,$route,$location,$rootScope,userService){
	
	   console.log("invoke user edit profile in controller");
	   $scope.passwordMismatch = false;
	   
	   if($rootScope.currentuser != undefined){
		   
		   userService.retrieveUserByUserId($rootScope.currentuser.c_user_id).then(function(response){
	    		
				$scope.user = response.data;
			},
			function(response){
				if(response.status == 401){
					$location.path("/login")
				}
				console.log(response.data.errorMessage);
				$scope.errormessage = response.data.errorMessage;
			})
	   }

		console.log("invoked register function in controller");
		
	    $scope.register=function(){
	    	if($scope.passwordRepeat !== $scope.user.password){
	    		console.log("Password and Confirm Password do not match.");
		   		$scope.errormessage = "Password and Confirm Password do not match.";
		   		$location.path('/register');
			}else{
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
	  }
	    
	  $scope.edituserprofile = function(){
		  if($scope.passwordRepeat !== $scope.user.password){
	    		console.log("Password and Confirm Password did not match.");
		   		$scope.errormessage = "Password and Confirm Password did not match.";
		   		$location.path('/register');
			}else{
		  userService.edituserprofile($scope.user).then(function(response){
	    		alert("User updated successfully")
				$location.path ('/home');
			},
			function(response){
				if(response.status == 401){
					$location.path("/login")
				}
				console.log(response.data.errorMessage);
				$scope.errormessage = response.data.errorMessage;
			})
		}
	  }
	  
	  $scope.checkPasswordMatch =function(){
		  $scope.passwordMismatch = false;
		  if($scope.passwordRepeat !== $scope.user.password){
			  $scope.passwordMismatch = true;
		  }
	  }
	  
	  	$scope.reset = function(){
			
			$scope.user = {};
		}
	 
});