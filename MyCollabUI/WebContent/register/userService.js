app.factory('userService',function($http,$rootScope){
	
	var BASE_URL = "http://localhost:8181/MyCollab";
	
	var userService = {
			
			registerUser:registerUser,
			retrieveUserByUserName:retrieveUserByUserName,
			retrieveUserByUserId:retrieveUserByUserId,
			edituserprofile:edituserprofile
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
	
	function retrieveUserByUserId(userId){
		
		console.log("Retrieve User By Id start");
		
		return $http.get(BASE_URL+"/retrieveUserById/"+userId)
		
	}
	
	function edituserprofile(user){
		
		console.log("Update User Profile")
		
		return $http.put(BASE_URL+"/updateUser",user)
	}
	
	function getImage(userId){
		
		console.log("Fetching User Image")
		
		return $http.get(BASE_URL+"/getimage/"+userId)
	}
	
	return userService;
	
});