app.controller('loginController',function($location, AuthenticationService,$rootScope){
	
	 var vm = this;
	 
     vm.login = login;
        
     (function initController() {
     	console.log("reset")
         // reset login status
         AuthenticationService.ClearCredentials();
     })();

     function login() {
        
         console.log("login func")
          AuthenticationService.Login(vm.username, vm.password, function (response) {	 
             if (response.success) {
            console.log("setcred")
            
             	AuthenticationService.SetCredentials(vm.username, vm.password);
                 $location.path('/home');
               $rootScope.islogged=true;
             	
             } else {
             	console.log("error")
               //  FlashService.Error(response.message);
             	
                 alert("error")
             }
         });
     };

});