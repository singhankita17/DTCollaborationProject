app.controller('userRegistrationController',function($scope,$route,$location,$rootScope,userService){
	
	   console.log("invoke user edit profile in controller");
	   $scope.passwordMismatch = false;
	   
	   if($rootScope.globals.currentUser != undefined){
		   retrieveUserDetail();
	   }
		   function retrieveUserDetail(){
			   console.log("retrieving user")
			   userService.retrieveUserByUserId($rootScope.globals.currentUser.userId).then(function(response){
				$scope.user = response.data;	
				console.log($scope.user )
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
		   		$scope.editUserDetailForm.$setPristine();
				$scope.editUserDetailForm.$setUntouched();
		   		$location.path('/register');
			}else{
	    	userService.registerUser($scope.user).then(function(response){
	    		console.log("User registered successfully");
				$scope.errormessage = "User registered successfully";
				$scope.registrationForm.$setPristine();
				$scope.registrationForm.$setUntouched();
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
		  console.log("inside user profile")
		  console.log($scope.user)
		  if($scope.passwordRepeat !== $scope.user.password){
	    		console.log("Password and Confirm Password did not match.");
		   		$scope.errormessage = "Password and Confirm Password did not match.";
		   		/*$scope.editUserDetailForm.$setPristine();
				$scope.editUserDetailForm.$setUntouched();*/
		   		$location.path('/editprofile');
			}else{
		  userService.edituserprofile($scope.user).then(function(response){
	    		alert("User updated successfully");
	    		$scope.editUserDetailForm.$setPristine();
				$scope.editUserDetailForm.$setUntouched();
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
	 
	  $scope.checkPasswordMatch = function(){
		  $scope.passwordMismatch = false;
		  console.log($scope.passwordRepeat)
		  console.log($scope.user.password)
		  if($scope.passwordRepeat !== $scope.user.password){
			  $scope.passwordMismatch = true;
			  $scope.errormessage = "";
		  }
	  }
	
	 
});