app.controller('LoginController', LoginController);

    LoginController.$inject = ['$scope','$location','AuthenticationService','$rootScope'];
 
    function LoginController($scope,$location, AuthenticationService,$rootScope) {
	 console.log("login controller")
       $scope.message=''
       
	 $scope.login = function() {
          // alert("login");
            console.log("login func")
             AuthenticationService.Login($scope.user, function (response) {	 
                if (response.success) {
             
              // alert("Success")
                	AuthenticationService.SetCredentials($scope.username, $scope.password);
                	 $rootScope.islogged=true;
                    $location.path('/home');            
                 
                	
                } else {
                	console.log("error"+response.message)
                  //  FlashService.Error(response.message);
                	$scope.message = response.message;
                //    alert("error")
                }
            });
        };
        
        
        $scope.logout = function() {
          //  alert("logout");
             console.log("logout func")
              AuthenticationService.Logout(function (response) {	 
                 if (response.success) {
                              
                 	AuthenticationService.ClearCredentials();
                     console.log("success"+response.data)
                   $rootScope.islogged=false;
                     $location.path('/login');
                 } else {
                	 
                   //  FlashService.Error(response.message);
                 //	     alert("error")
                	 $scope.error = response.data;
                        console.log(response.data)
                		 AuthenticationService.ClearCredentials();
                         console.log("User is invalid"+response.data)
                         $rootScope.islogged=false;
                         $location.path('/login');
                	
                 }
             });
         };
       
    }
