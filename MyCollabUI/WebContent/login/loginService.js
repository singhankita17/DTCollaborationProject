app.factory('AuthenticationService', AuthenticationService);

var BASE_URL = 'http://localhost:8181/MyCollab';

    AuthenticationService.$inject = ['$http', '$cookieStore', '$rootScope'];
    function AuthenticationService($http, $cookieStore, $rootScope) {
        var service = {};
 
        service.Login = Login;
        service.Logout=Logout;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
        service.getNotificationNotViewed = getNotificationNotViewed;
        service.getNotificationViewed = getNotificationViewed;
        service.updateNotification = updateNotification;
                
 
        return service;
 
        function Login(user, callback) {
        	//alert("inside service"+user)
        	$http.post(BASE_URL+'/login',user)
    		  .then(function (response,data,status,headers,config) {
    			  $rootScope.isAdmin = false; 			  
    			$rootScope.currentuser=response.data;
    			if($rootScope.currentuser.role == 'ADMIN')
    				$rootScope.isAdmin = true;
    			 console.log("response data: "+$rootScope.currentuser.firstName)
    			if(response!=null){
    				 
    				 response = { success: true, data: response.data };
                } else {
                    response = { success: false, message: response.data.errorMessage };
                }
                    callback(response);
                },function(response,data,status,headers,config) {
    			$rootScope.currentuser='';
    			//alert("inside error")
    			console.log(response.data)
    			if(response!=null){
    				
    				 response = { success: false, message: response.data.errorMessage };
                }
                    callback(response);
                }) 
        }
        
        
        function Logout(callback){
        	//alert("inside logout func");
        	$http({
        		method:'GET',
        		url:BASE_URL+'/logout'
        	}).then(function(response,data,status,headers,config){
        		//alert("inside logout success : "+response.data);
        		response = {success:true};
        		callback(response);
        		
        	},function(response,data,status,headers,config){
        		
        		//alert("inside Logout error");
        		response = {success:false};
        		callback(response);
        	});
        }
 
        function SetCredentials(username, password) {
        	//alert("inside set credential");
        	 var authdata = Base64.encode(username + ':' + password);
             $rootScope.globals = {
                 currentUser: {
                     username: $rootScope.currentuser.userName,
                     authdata: authdata,
                     islogged:true,
                     role:$rootScope.currentuser.role,
                     firstname: $rootScope.currentuser.firstName,
                     userId: $rootScope.currentuser.c_user_id
                 }
            };
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
           // $cookieStore.put("currentuser",$rootScope.currentuser);
           // alert("credential successfully set");
        }
 
        function ClearCredentials() {
        	//alert("inside clear credential");
            $rootScope.globals = {};
            $cookieStore.remove('globals');
           // $cookieStore.remove('currentuser');
           // $rootScope.currentuser={}
            $http.defaults.headers.common.Authorization = 'Basic';
            //alert("credential cleared");
        }
        
        
        function getNotificationNotViewed(callback){
    		//alert("inside not viewed service");
    		$http.get(BASE_URL+"/getnotification/"+0).then(function(response,data,status,headers,config){
        		response = {success:true,data: response.data };
        		callback(response);
        		
        	},function(response,data,status,headers,config){
        		response = {success:false,data: response.data };
        		callback(response);
        	});
    	}
    	
    	function getNotificationViewed(callback){
    		//alert("inside viewed service")
    		$http.get(BASE_URL+"/getnotification/"+1).then(function(response,data,status,headers,config){
        		response = {success:true,data: response.data };
        		callback(response);
        		
        	},function(response,data,status,headers,config){
        		
        		response = {success:false,data: response.data };
        		callback(response);
        	});
    	}
    	
    	function updateNotification(notificationId,callback){
    		$http.get(BASE_URL+"/updatenotification/"+notificationId).then(function(response,data,status,headers,config){
        		response = {success:true,data: response.data };
        		callback(response);
        		
        	},function(response,data,status,headers,config){
        		
        		response = {success:false,data: response.data };
        		callback(response);
        	});
    	}
    }
    var Base64 = {
    		 
            keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
     
            encode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;
     
                do {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
     
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
     
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
     
                    output = output +
                        this.keyStr.charAt(enc1) +
                        this.keyStr.charAt(enc2) +
                        this.keyStr.charAt(enc3) +
                        this.keyStr.charAt(enc4);
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
                } while (i < input.length);
     
                return output;
            },
     
            decode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;
     
                // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
                var base64test = /[^A-Za-z0-9\+\/\=]/g;
                if (base64test.exec(input)) {
                    window.alert("There were invalid base64 characters in the input text.\n" +
                        "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                        "Expect errors in decoding.");
                }
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
     
                do {
                    enc1 = this.keyStr.indexOf(input.charAt(i++));
                    enc2 = this.keyStr.indexOf(input.charAt(i++));
                    enc3 = this.keyStr.indexOf(input.charAt(i++));
                    enc4 = this.keyStr.indexOf(input.charAt(i++));
     
                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;
     
                    output = output + String.fromCharCode(chr1);
     
                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }
     
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
     
                } while (i < input.length);
     
                return output;
            }
        };