app.factory('userService',function($http){
	
	var BASE_URL = "http://localhost:8181/MyCollab";
	
	var userService = {
			
			registerUser:registerUser
	};
	
	/*userService.registerUser = registerUser;
	*/
	function registerUser(user){
		
		console.log("registration start");
		
		return $http.post(BASE_URL+"/registration",user)
		
	}
	
	return userService;
	
});