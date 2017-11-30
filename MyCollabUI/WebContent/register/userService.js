app.factory('userService',function($http,$rootScope){
	
	var BASE_URL = "http://localhost:8181/MyCollab";
	
	var userService = {
			
			registerUser:registerUser,
			retrieveUserByUserName:retrieveUserByUserName
	};
	
	/*userService.registerUser = registerUser;
	*/
	
	function registerUser(user){
		
		console.log("registration start");
		
		return $http.post(BASE_URL+"/registration",user)
		
	}
	
	function retrieveUserByUserName(username){
		
		console.log("Retrieve User start");
		
		return $http.get(BASE_URL+"/retrieveUser/"+username)
		
	}
	
	return userService;
	
});