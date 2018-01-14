app.controller('LoginController', LoginController);

    LoginController.$inject = ['$scope','$location','AuthenticationService','$rootScope','$window'];
 
    function LoginController($scope,$location, AuthenticationService,$rootScope,$window) {
	 console.log("login controller")
       $scope.message=''
       
	 $scope.login = function() {
          // alert("login");
            console.log("login func")
             AuthenticationService.Login($scope.user, function (response) {	 
                if (response.success) {
             
            //  alert("Success")
                	AuthenticationService.SetCredentials($scope.username, $scope.password);
                	 $rootScope.islogged=true;
                	 if($rootScope.currentuser.role === 'ADMIN'){
                		 $location.path('/admin/home');    
                	 }else{
                		 
                		 $location.path('/home');    

                	     	getNotifications();
                	 }
                	
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
                     $rootScope.isAdmin = false;
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
         
         
         function getNotifications(){
     		
        	 AuthenticationService.getNotificationNotViewed(function(response){
        		 if(response.success){
     			console.log(response.data)
     			$rootScope.notificationNotViewed = response.data;
     			$rootScope.notificationNotViewedLength = response.data.length;
        		 }else{
	     			console.log(response.data)
	     			if(response.status==401){
	     				$location.path("/login");
	     			}
        		 }
     		})
     		
     		
     		AuthenticationService.getNotificationViewed(function(response){
     			 if(response.success){
     			console.log(response.data)
     			$rootScope.notificationViewed = response.data;
     		}else{
     			console.log(response.data)
     			if(response.status==401){
     				$location.path("/login");
     			}
     		}
     		})
     	}
     	     	
     	$rootScope.updateLength = function(){
     		$rootScope.notificationNotViewedLength = 0;
     	}
     	
     	$rootScope.updateNotification = function(notificationId){
     		
     		AuthenticationService.updateNotification(notificationId,function(response){
     			 if(response.success){
     			getNotifications();
     		}else{
     			console.log(response.data)
     			if(response.status==401){
     				$location.path("/login");
     			}
     		}
     		})
     	}

         

       }
