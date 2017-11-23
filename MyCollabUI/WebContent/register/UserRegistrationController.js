app.controller('userRegistrationController',function($scope,$route,$location,$rootScope,userService){

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
});